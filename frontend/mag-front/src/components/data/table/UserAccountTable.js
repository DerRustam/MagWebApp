import React from "react"
import {Table} from "react-bootstrap";
import {strings} from "../../../locale/strings";

export default function getUserAccountTable(dataList) {
    return(
        <Table striped bordered hover>
            <thead>
                <tr>
                    <th>View Nickname</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Date Birth</th>
                    <th>Gender</th>
                    <th>About</th>
                    <th>Is Locked</th>
                    <th>Roles</th>
                    <th>{strings("L_DB_OPR")}</th>
                </tr>
            </thead>
            <tbody>
            {
                dataList.map((record) => {
                    return <tr key={record.id}>
                        <td>{record.viewNickname}</td>
                        <td>{record.firstName}</td>
                        <td>{record.lastName}</td>
                        <td>{record.dateBirth}</td>
                        <td>{record.gender}</td>
                        <td>{record.about}</td>
                        <td>{record.isLocked}</td>
                        <td>{record.roles.map(r => <div key={r.id}>{r.nameRole+" /"}</div>)}</td>
                        <td></td>
                    </tr>;
                })
            }
            </tbody>
        </Table>
    );
}