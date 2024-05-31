import {useEffect, useState} from "react";
import {Link, useNavigate, useParams} from "react-router-dom";
import AppNavBar from "./AppNavBar";
import {Button, Container, Form, FormGroup, Input, Label} from "reactstrap";


const PersonEdit = () => {
    const initialFormState = {
        name: ''
    };
    const [person, setPerson] = useState(initialFormState);
    const navigate = useNavigate();
    const { id } = useParams();

    useEffect( () => {
        if (id !== 'new') {
            fetch(`${id}`)
                .then(response => response.json())
                .then(data => setPerson(data));
        }
    }, [id, setPerson]);

    const handleChange = (event) => {
        const { name, value } = event.target

        setPerson( { ...person, [name]: value })
    }

    const handleSubmit = async (event) => {
        event.preventDefault();
        await fetch(
            (person.id) ?
                `UpdatePerson/${person.id}`
                : `AddPerson`,
            {
                method: (person.id) ? 'PUT' : 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(person)
            });
        setPerson(initialFormState);
        navigate(`/persons`);
    }

    const title = <h2>{person.id ? 'Edit Person' : 'Add Person'}</h2>

    return (<div>
        <AppNavBar/>
        <Container>
            {title}
            <Form onSubmit={handleSubmit}>
                <FormGroup>
                    <Label for="name">Name</Label>
                    <Input type="text" name="name" value={person.name || ''}
                           onChange={handleChange} autoComplete="name"/>
                </FormGroup>
                <FormGroup>
                    <Button color="primary" type="submit">Save</Button>{' '}
                    <Button color="secondary" tag={Link} to="/persons">Cancel</Button>
                </FormGroup>
            </Form>
        </Container>
    </div>)
}

export default PersonEdit;