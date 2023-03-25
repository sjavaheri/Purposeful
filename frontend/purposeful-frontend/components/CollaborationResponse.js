import {
    Popover, PopoverTrigger, PopoverContent, PopoverArrow, PopoverCloseButton, PopoverHeader, PopoverBody,
    Button, Badge
} from '@chakra-ui/react';

export default function CollaborationResponse({ collaborationResponse }) {
    // const message = collaborationResponse.message;
    // var additionalContact = collaborationResponse.additionalContact;
    const message = "I would love to work with you!";
    var additionalContact = "123-456-7890";

    if (additionalContact === "") {
        additionalContact = null;
    } else {
        additionalContact = (
            <div>
                <b>Additional Contact:</b> {additionalContact}
            </div>
        )
    }

    // var status = collaborationResponse.status;
    var status = "Approved";

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
                    {message} <br />
                    {additionalContact}
                </PopoverBody>
            </PopoverContent>
        </Popover>
    );
}