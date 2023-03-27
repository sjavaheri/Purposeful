import {
    Popover, PopoverTrigger, PopoverContent, PopoverArrow, PopoverCloseButton, PopoverHeader, PopoverBody,
    Button, Badge
} from '@chakra-ui/react';
import fetchWrapper from '@/utils/fetch_wrapper';
import { useEffect, useState } from 'react';

export default function CollaborationResponse({ ideaId }) {
    const [collaborationResponse, setCollaborationResponse] = useState({});

    // fetch data
    useEffect(() => {
        const fetchCollaborationResponse = async () => {
            const response = await fetchWrapper('/api/collaborationResponse/' + ideaId)

            const handleData = async (response) => {
                if (!response.ok) {
                    return null;
                } else {
                    return await response.json();
                }
            }

            setCollaborationResponse(await handleData(response));
        };
        fetchCollaborationResponse();
    }, [ideaId]);

    // error has occurred, meaning that either there is no response yet or the user did not send a request
    if (collaborationResponse === null) {
        return null;
    }

    // message
    var message = collaborationResponse.message;
    message = (
        <div>
            <b>Message:</b> {message}
        </div>
    )

    // contact
    var additionalContact = collaborationResponse.additionalContact;
    if (additionalContact === "") {
        additionalContact = null;
    } else {
        additionalContact = (
            <div>
                <b>Contact Information:</b> {additionalContact}
            </div>
        )
    }

    // status
    var status = collaborationResponse.status;
    if (status === "Approved") {
        status = (
            <Badge colorScheme="green">Approved</Badge>
        )
    } else if (status === "Declined") {
        status = (
            <Badge colorScheme="red">Declined</Badge>
        )
    } else {
        status = null;
    }

    return (
        <Popover>
            <PopoverTrigger>
                <Button>View Response</Button>
            </PopoverTrigger>
            <PopoverContent>
                <PopoverArrow />
                <PopoverCloseButton />
                <PopoverHeader>Response {status}</PopoverHeader>
                <PopoverBody>
                    {additionalContact} <br />
                    {message}
                </PopoverBody>
            </PopoverContent>
        </Popover>
    );
}