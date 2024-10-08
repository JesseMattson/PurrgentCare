import {useEffect, useState} from "react";
import {Link, useNavigate, useParams} from "react-router-dom";
import AppNavBar from "./AppNavBar";
import {Button, Container, Form, FormGroup, Input, Label} from "reactstrap";
import {ACCOUNT_BASE_URL, PERSON_BASE_URL} from "./constants";

const AccountEdit = () => {
    const initialFormState = {
        id: '',
        active: true,
        dateCreated: new Date().toISOString(),
        accountHolders: []
    };

    const [account, setAccount] = useState(initialFormState);
    const [persons, setPersons] = useState([]);
    const navigate = useNavigate();
    const { id } = useParams();

    useEffect( () => {
        if (id !== 'new') {
            fetch(`${ACCOUNT_BASE_URL}/${id}`)
                .then(response => response.json())
                .then(data => setAccount({
                    ...data,
                    dateCreated: data.dateCreated,
                    active: data.active,
                    accountHolders: data.accountHolders.map(person => person.id) // Map existing holders by ID
                }));
        }

        fetch(PERSON_BASE_URL)
            .then(response => response.json())
            .then(data => setPersons(data));
    }, [id, setAccount]);

    const handleChange = (event) => {
        const { name, value } = event.target

        setAccount( { ...account, [name]: value })
    }

    const handleAccountHoldersChange = (event) => {
        const selectedIds = Array.from(event.target.selectedOptions, option => option.value);
        setAccount({ ...account, accountHolders: selectedIds });
    };

    const handleSubmit = async (event) => {
        event.preventDefault();

        const accountToSubmit = {
            accountId: account.id,  // Rename id to accountId
            active: account.active,
            dateCreated: account.dateCreated,
            personIds: account.accountHolders, // Send personIds as an array of IDs
            pets: account.pets
        };

        await fetch(
            accountToSubmit.accountId ?
                `${ACCOUNT_BASE_URL}/associate-people`
                : `${ACCOUNT_BASE_URL}`,
            {
                method: accountToSubmit.accountId ? 'PUT' : 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(accountToSubmit)
            });
        setAccount(initialFormState);
        navigate(`${ACCOUNT_BASE_URL}`);
    };


    const title = <h2>{account.id ? 'Edit Account' : 'Add Account'}</h2>;

    return (
        <div>
            <AppNavBar />
            <Container>
                {title}
                <Form onSubmit={handleSubmit}>
                    <FormGroup>
                        <Label for="active">Active</Label>
                        <Input
                            id="active"
                            type="text"
                            name="active"
                            value={account.active.toString()}
                           onChange={handleChange} autoComplete="active"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="accountHolders">Account Holders</Label>
                        <Input
                            type="select"
                            name="accountHolders"
                            id="accountHolders"
                            multiple
                            value={account.accountHolders}
                            onChange={handleAccountHoldersChange}>
                            {persons.map(person => (
                                <option key={person.id} value={person.id}>
                                    {person.name}
                                </option>
                            ))}
                        </Input>
                    </FormGroup>

                    <FormGroup>
                        <Button color="primary" type="submit">Save</Button>{' '}
                        <Button color="secondary" tag={Link} to={`${ACCOUNT_BASE_URL}`}>Cancel</Button>
                    </FormGroup>
                </Form>
            </Container>
        </div>
    );
};

export default AccountEdit;
