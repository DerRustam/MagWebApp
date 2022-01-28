import React from "react"
import {Table} from "react-bootstrap";
import {strings} from "../../../locale/strings";

export default function getRolesTable(dataList) {
    return(
        <Table striped bordered hover>
            <thead>
                <tr>
                    <th>Name Role</th>
                    <th>Role Description</th>
                    <th>Granted Privileges</th>
                    <th>{strings("L_DB_OPR")}</th>
                </tr>
            </thead>
            <tbody>
            {
                dataList.map((record, _) => {
                    return <tr key={record.id}>
                        <td>{record.nameRole}</td>
                        <td>{record.descriptionRole}</td>
                        <td>{record.privileges.map(p => <div key={p.id}>{p.namePrivilege+" /"}</div>)}</td>
                        <td></td>
                    </tr>;
                })
            }
            </tbody>
        </Table>
    );
}