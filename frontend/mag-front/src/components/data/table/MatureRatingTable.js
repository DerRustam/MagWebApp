import React from "react"
import {Table} from "react-bootstrap";
import {strings} from "../../../locale/strings";

export default function getMatureRatingTable(dataList) {
    return(
        <Table striped bordered hover>
            <thead>
                <tr>
                    <th>Name Rating</th>
                    <th>Mature Rating Description</th>
                    <th>{strings("L_DB_OPR")}</th>
                </tr>
            </thead>
            <tbody>
            {
                dataList.map((record, _) => {
                    return <tr id={record.id}>
                        <td>{record.nameRating}</td>
                        <td>{record.descriptionRating}</td>
                        <td></td>
                    </tr>;
                })
            }
            </tbody>
        </Table>
    );
}