import React, { useEffect, useState } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavBar';
import { Link } from 'react-router-dom';

const PersonList = () => {

    const [persons, setPersons] = useState([]);
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        setLoading(true);

        fetch('persons/all')
            .then(response => response.json())
            .then(data => {
                setPersons(data);
                setLoading(false);
            })
    }, []);

    const remove = async (id) => {
        await fetch(`/persons/DeletePerson/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            credentials: 'include'
        }).then(() => {
            let updatedPersons = [...persons].filter(i => i.id !== id);
            setPersons(updatedPersons);
        });
    }

    if (loading) {
        return <p>Loading...</p>;
    }

    const personList = persons.map(person => {
        return <tr key={person.id}>
            <td style={{ whiteSpace: 'nowrap' }}>{person.name}</td>
            <td>
                <ButtonGroup>
                    <Button size="sm" color="primary" tag={Link} to={"/persons/" + person.id}>Edit</Button>
                    <Button size="sm" color="danger" onClick={() => remove(person.id)}>Delete</Button>
                </ButtonGroup>
            </td>
        </tr>
    });

    return (
        <div>
            <AppNavbar/>
            <Container fluid>
                <div className="float-end">
                    <Button color="success" tag={Link} to="/persons/new">Add person</Button>
                </div>
                <h3>Customers</h3>
                <Table className="mt-4">
                    <thead>
                    <tr>
                        <th width="50%">Name</th>
                        <th width="20%">Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    {personList}
                    </tbody>
                </Table>
            </Container>
        </div>
    );
};

export default PersonList;
