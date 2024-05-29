import {useEffect, useState} from "react";
import {Link, useNavigate, useParams} from "react-router-dom";
import AppNavBar from "./AppNavBar";
import {Button, Container, Form, FormGroup, Input, Label} from "reactstrap";


const PetEdit = () => {
    const initialFormState = {
        name: ''
    };
    const [pet, setPet] = useState(initialFormState);
    const navigate = useNavigate();
    const { id } = useParams();

    useEffect( () => {
        if (id !== 'new') {
            fetch(`/pet/${id}`)
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
                `/pet/UpdatePet/${pet.id}`
                : `/pet/AddPet`,
            {
                method: (pet.id) ? 'PUT' : 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(pet)
            });
        setPet(initialFormState);
        navigate(`/pets`);
    }

    const title = <h2>{pet.id ? 'Edit Pet' : 'Add Pet'}</h2>

    return (<div>
        <AppNavBar/>
        <Container>
            {title}
            <Form onSubmit={handleSubmit}>
                <FormGroup>
                    <Label for="name">Name</Label>
                    <Input type="text" name="name" value={pet.name || ''}
                           onChange={handleChange} autoComplete="name"/>
                </FormGroup>
                <FormGroup>
                    <Label for="type">Type</Label>
                    <Input type="text" name="type" value={pet.type || ''}
                           onChange={handleChange} autoComplete="type"/>
                </FormGroup>
                <FormGroup>
                    <Label for="age">Age</Label>
                    <Input type="text" name="age" value={pet.age || ''}
                           onChange={handleChange} autoComplete="age"/>
                </FormGroup>
                <FormGroup>
                    <Label for="gender">Gender</Label>
                    <Input type="text" name="gender" value={pet.gender || ''}
                           onChange={handleChange} autoComplete="gender"/>
                </FormGroup>
                <FormGroup>
                    <Button color="primary" type="submit">Save</Button>{' '}
                    <Button color="secondary" tag={Link} to="/pets">Cancel</Button>
                </FormGroup>
            </Form>
        </Container>
    </div>)
}

export default PetEdit;