import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavBar';
import { Link } from 'react-router-dom';
import {useEffect, useState} from "react";
import {ACCOUNT_BASE_URL} from "./constants";

const AccountList = () => {

    const [accounts, setAccounts] = useState([]);
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        setLoading(true);

        fetch(`${ACCOUNT_BASE_URL}`)
            .then(response => response.json())
            .then(data => {
                setAccounts(data);
                setLoading(false);
            })
    }, []);

    const remove = async (id) => {
        await fetch(`${ACCOUNT_BASE_URL}/${id}`, {
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
            <td style={{ whiteSpace: 'nowrap' }}>{account.id}</td>
            <td style={{ whiteSpace: 'nowrap' }}>{account.active.toString()}</td>
            <td style={{ whiteSpace: 'nowrap' }}>{account.dateCreated}</td>
            <td>
                <ButtonGroup>
                    <Button size="sm" color="primary" tag={Link} to={`${ACCOUNT_BASE_URL}/${account.id}`}>Edit</Button>
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
                    <Button color="success" tag={Link} to={`${ACCOUNT_BASE_URL}/new`}>Add Account</Button>
                </div>
                <h3>Accounts</h3>
                <Table className="mt-4">
                    <thead>
                    <tr>
                        <th width="40%">Account Number</th>
                        <th width="15%">Active</th>
                        <th width="15%">Date Created</th>
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
