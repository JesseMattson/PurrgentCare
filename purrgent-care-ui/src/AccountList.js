import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavBar';
import { Link } from 'react-router-dom';
import {useEffect, useState} from "react";

const AccountList = () => {

    const [accounts, setAccounts] = useState([]);
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        setLoading(true);

        fetch('accounts/all')
            .then(response => response.json())
            .then(data => {
                setAccounts(data);
                setLoading(false);
            })
    }, []);

    const remove = async (id) => {
        await fetch(`/accounts/DeleteAccount/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            credentials: 'include'
        }).then(() => {
            let updatedAccounts = [...accounts].filter(i => i.id !== id);
            setAccounts(updatedAccounts);
        });
    }

    if (loading) {
        return <p>Loading...</p>;
    }

    const accountList = accounts.map(account => {
        return <tr key={account.id}>
            <td style={{ whiteSpace: 'nowrap' }}>{account.name}</td>
            <td>
                <ButtonGroup>
                    <Button size="sm" color="primary" tag={Link} to={"/accounts/" + account.id}>Edit</Button>
                    <Button size="sm" color="danger" onClick={() => remove(account.id)}>Delete</Button>
                </ButtonGroup>
            </td>
        </tr>
    });

    return (
        <div>
            <AppNavbar/>
            <Container fluid>
                <div className="float-end">
                    <Button color="success" tag={Link} to="/accounts/new">Add Account</Button>
                </div>
                <h3>Accounts</h3>
                <Table className="mt-4">
                    <thead>
                    <tr>
                        <th width="50%">Name</th>
                        <th width="20%">Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    {accountList}
                    </tbody>
                </Table>
            </Container>
        </div>
    );
};

export default AccountList;
