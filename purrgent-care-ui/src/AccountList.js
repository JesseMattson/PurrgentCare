import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavBar';
import { Link } from 'react-router-dom';
import {useEffect, useState} from "react";
import {ACCOUNT_BASE_URL} from "./constants";
import { format } from "date-fns";

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

    const toggle_status = async (id) => {
        await fetch(`${ACCOUNT_BASE_URL}/status/${id}`, {
            method: 'PUT',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            credentials: 'include'
        }).then(() => {
            let updatedAccounts = [...accounts];
            setAccounts(updatedAccounts);
            window.location.reload()
        })
    }

    if (loading) {
        return <p>Loading...</p>;
    }

    const accountList = accounts.map(account => {
        return <tr key={account.id}>
            <td style={{ whiteSpace: 'nowrap' }}>{account.id}</td>
            <td style={{ whiteSpace: 'nowrap' }}>{account.active.toString()}</td>
            <td style={{ whiteSpace: 'nowrap' }}>{format(account.dateCreated, 'yyyy/MM/dd')}</td>
            <td>
                <ButtonGroup>
                    <Button size="sm" color={account.active ? "danger" : "success"} onClick={() =>
                        toggle_status(account.id)}>{account.active ? "Disable" : "Enable"}</Button>
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
