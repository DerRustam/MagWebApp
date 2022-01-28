import React from "react"
import {Table} from "react-bootstrap";
import {strings} from "../../../locale/strings";

export default function getPrivilegesTable(dataList) {
    return(
        <Table striped bordered hover>
            <thead>
                <tr>
                    <th>Name Privilege</th>
                    <th>Privilege Description</th>
                    <th>{strings("L_DB_OPR")}</th>
                </tr>
            </thead>
            <tbody>
            {
                dataList.map((record, _) => {
                return <tr key={record.id}>
                    <td>{record.namePrivilege}</td>
                    <td>{record.descriptionPrivilege}</td>
                    <td></td>
                </tr>;
            })
            }
            </tbody>
        </Table>
    );
}