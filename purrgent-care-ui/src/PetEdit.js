import {useEffect, useState} from "react";
import {Link, useNavigate, useParams} from "react-router-dom";
import AppNavBar from "./AppNavBar";
import {Button, Container, Form, FormGroup, Input, Label} from "reactstrap";
import {PET_BASE_URL} from "./constants";


const PetEdit = () => {
    const initialFormState = {
        name: '',
        type: '',
        age: '',
        gender: ''
    };
    const [pet, setPet] = useState(initialFormState);
    const navigate = useNavigate();
    const { id } = useParams();

    useEffect( () => {
        if (id !== 'new') {
            fetch(`${PET_BASE_URL}/${id}`)
                .then(response => response.json())
                .then(data => setPet(data));
        }
    }, [id, setPet]);

    const handleChange = (event) => {
        const { name, value } = event.target

        setPet( { ...pet, [name]: value })
    }

    const handleSubmit = async (event) => {
        event.preventDefault();
        await fetch(
            (pet.id) ?
                `${PET_BASE_URL}/${pet.id}`
                : `${PET_BASE_URL}`,
            {
                method: (pet.id) ? 'PUT' : 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(pet)
            });
        setPet(initialFormState);
        navigate(`${PET_BASE_URL}`);
    }

    const title = <h2>{pet.id ? 'Edit Pet' : 'Add Pet'}</h2>

    return (<div>
        <AppNavBar/>
        <Container>
            {title}
            <Form onSubmit={handleSubmit}>
                <FormGroup>
                    <Label for="name">Name</Label>
                    <Input id="name" type="text" name="name" value={pet.name || ''}
                           onChange={handleChange} autoComplete="name"/>
                </FormGroup>
                <FormGroup>
                    <Label for="type">Type</Label>
                    <Input id="type" type="text" name="type" value={pet.type || ''}
                           onChange={handleChange} autoComplete="type"/>
                </FormGroup>
                <FormGroup>
                    <Label for="age">Age</Label>
                    <Input id="age" type="text" name="age" value={pet.age || ''}
                           onChange={handleChange} autoComplete="age"/>
                </FormGroup>
                <FormGroup>
                    <Label for="gender">Gender</Label>
                    <Input id="gender" type="text" name="gender" value={pet.gender || ''}
                           onChange={handleChange} autoComplete="gender"/>
                </FormGroup>
                <FormGroup>
                    <Button color="primary" type="submit">Save</Button>{' '}
                    <Button color="secondary" tag={Link} to={`${PET_BASE_URL}`}>Cancel</Button>
                </FormGroup>
            </Form>
        </Container>
    </div>)
}

export default PetEdit;