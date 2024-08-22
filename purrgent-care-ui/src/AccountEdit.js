import {useEffect, useState} from "react";
import {Link, useNavigate, useParams} from "react-router-dom";
import AppNavBar from "./AppNavBar";
import {Button, Container, Form, FormGroup, Input, Label} from "reactstrap";
import {ACCOUNT_BASE_URL} from "./constants";


const AccountEdit = () => {
    const initialFormState = {
        id: '',
        active: '',
        dateCreated: ''
    };
    const [account, setAccount] = useState(initialFormState);
    const navigate = useNavigate();
    const { id } = useParams();

    useEffect( () => {
        if (id !== 'new') {
            fetch(`${ACCOUNT_BASE_URL}/${id}`)
                .then(response => response.json())
                .then(data => setAccount(data));
        }
    }, [id, setAccount]);

    const handleChange = (event) => {
        const { name, value } = event.target

        setAccount( { ...account, [name]: value })
    }

    const handleSubmit = async (event) => {
        event.preventDefault();
        await fetch(
            (account.id) ?
                `${ACCOUNT_BASE_URL}/${account.id}`
                : `${ACCOUNT_BASE_URL}`,
            {
                method: (account.id) ? 'PUT' : 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(account)
            });
        setAccount(initialFormState);
        navigate(`${ACCOUNT_BASE_URL}`);
    }

    const title = <h2>{account.id ? 'Edit Account' : 'Add Account'}</h2>

    return (<div>
        <AppNavBar/>
        <Container>
            {title}
            <Form onSubmit={handleSubmit}>
                <FormGroup>
                    <Label for="active">Active</Label>
                    <Input id="active" type="text" name="active" value={account.active.toString() || ''}
                           onChange={handleChange} autoComplete="active"/>
                </FormGroup>
                <FormGroup>
                    <Button color="primary" type="submit">Save</Button>{' '}
                    <Button color="secondary" tag={Link} to={`${ACCOUNT_BASE_URL}`}>Cancel</Button>
                </FormGroup>
            </Form>
        </Container>
    </div>)
}

export default AccountEdit;