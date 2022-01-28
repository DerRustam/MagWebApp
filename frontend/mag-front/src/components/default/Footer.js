import React from "react";
import {Container} from "react-bootstrap"

export default function Footer() {
    return (
        <footer className="py-2 bg-dark fixed-bottom">
            <Container>
                <p className="m-0 text-center text-white">
                    Sagitov R. (gr. 6223)
                </p>
            </Container>
        </footer>
    );
}