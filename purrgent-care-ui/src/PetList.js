import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavBar';
import { Link } from 'react-router-dom';
import {useEffect, useState} from "react";

const PetList = () => {

    const [pets, setPets] = useState([]);
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        setLoading(true);

        fetch('pet/all')
            .then(response => response.json())
            .then(data => {
                setPets(data);
                setLoading(false);
            })
    }, []);

    const remove = async (id) => {
        await fetch(`/pets/DeletePet/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            credentials: 'include'
        }).then(() => {
            let updatePets = [...pets].filter(i => i.id !== id);
            setPets(updatePets);
        });
    }

    if (loading) {
        return <p>Loading...</p>;
    }

    const petList = pets.map(pet => {
        return <tr key={pet.id}>
            <td style={{ whiteSpace: 'nowrap' }}>{pet.name}</td>
            <td style={{ whiteSpace: 'nowrap' }}>{pet.type}</td>
            <td style={{ whiteSpace: 'nowrap' }}>{pet.age}</td>
            <td style={{ whiteSpace: 'nowrap' }}>{pet.gender}</td>
            <td>
                <ButtonGroup>
                    <Button size="sm" color="primary" tag={Link} to={"/pets/" + pet.id}>Edit</Button>
                    <Button size="sm" color="danger" onClick={() => remove(pet.id)}>Delete</Button>
                </ButtonGroup>
            </td>
        </tr>
    });

    return (
        <div>
            <AppNavbar/>
            <Container fluid>
                <div className="float-end">
                    <Button color="success" tag={Link} to="/pets/new">Add pet</Button>
                </div>
                <h3>Pets</h3>
                <Table className="mt-4">
                    <thead>
                    <tr>
                        <th width="25%">Name</th>
                        <th width="25%">Type</th>
                        <th width="15%">Age</th>
                        <th width="15%">Gender</th>
                        <th width="20%">Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    {petList}
                    </tbody>
                </Table>
            </Container>
        </div>
    );
};

export default PetList;
