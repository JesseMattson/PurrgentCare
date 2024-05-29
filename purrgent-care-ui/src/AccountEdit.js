import {useEffect, useState} from "react";
import {Link, useNavigate, useParams} from "react-router-dom";
import AppNavBar from "./AppNavBar";
import {Button, Container, Form, FormGroup, Input, Label} from "reactstrap";


const AccountEdit = () => {
    const initialFormState = {
        name: ''
    };
    const [account, setAccount] = useState(initialFormState);
    const navigate = useNavigate();
    const { id } = useParams();

    useEffect( () => {
        if (id !== 'new') {
            fetch(`${id}`)
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
                `UpdateAccount/${account.id}`
                : `AddAccount`,
            {
                method: (account.id) ? 'PUT' : 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(account)
            });
        setAccount(initialFormState);
        navigate(`/accounts`);
    }

    const title = <h2>{account.id ? 'Edit Account' : 'Add Account'}</h2>

    return (<div>
        <AppNavBar/>
        <Container>
            {title}
            <Form onSubmit={handleSubmit}>
                <FormGroup>
                    <Label for="name">Name</Label>
                    <Input type="text" name="name" value={account.name || ''}
                           onChange={handleChange} autoComplete="name"/>
                </FormGroup>
                <FormGroup>
                    <Button color="primary" type="submit">Save</Button>{' '}
                    <Button color="secondary" tag={Link} to="/accounts">Cancel</Button>
                </FormGroup>
            </Form>
        </Container>
    </div>)
}

export default AccountEdit;