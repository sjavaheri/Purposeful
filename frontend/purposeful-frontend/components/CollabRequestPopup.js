import React from 'react';
import { Input, Box, Text, Button, Stack, Flex, IconButton, Textarea } from '@chakra-ui/react';
import { RxCross1 } from "react-icons/rx";
import { useColorModeValue } from '@chakra-ui/react';
import { BsSend } from "react-icons/bs";
import { useBoolean } from "@chakra-ui/react";
import fetchWrapper from "@/utils/fetch_wrapper";
import notification from "@/utils/notification";

export default function CollabRequestPopup({ RequesterMail, ideaID, ideaTitle, removeFunc }) {
    const col = useColorModeValue("rgb(255,255,255)", "rgb(26,32,44)");
    const bannercol = useColorModeValue("rgb(237,242,247)", "rgb(23,25,35)");

    const [buttonLoading, setButtonLoading] = useBoolean(false);

    const handleSend = async () => {

        setButtonLoading.on();

        const payload = {
            ideaId: ideaID,
            message: document.getElementById("reqMessage").value,
            additionalContact: document.getElementById("addContact").value
        };

        let response = null;

        response = await fetchWrapper(
            "/api/collaborationRequest",
            null,
            "POST",
            payload
        );

        if (!response.ok) {
            notification("error", response.errorMessages);
        } else {
            notification("success", "Request sent!");
        }

        setButtonLoading.off();
    };

    return (
        <Box zIndex={"10"} backgroundColor={col} marginTop={"15%"} marginLeft={"3%"} borderColor={"rgba(0,0,0,0.1)"} width={"40%"} alignSelf={"center"} position={"fixed"} borderRadius={"10px"}>
            <Stack>
                <Box width={"100%"}>
                    <Flex width={"100%"} backgroundColor={bannercol} borderRadius={"10px"}>
                        <Text margin={"10px"}>Collaboration Request</Text>
                        <IconButton
                            margin={"10px"}
                            marginLeft={"auto"}
                            size={'xs'}
                            icon={<RxCross1 />}
                            onClick={() => removeFunc()}
                        />
                    </Flex>
                </Box>
                <Flex width={"80%"} alignSelf={"center"}>
                    <Text marginLeft={"0"} marginRight={"auto"}>User Email: {RequesterMail}</Text>
                    <Text marginLeft={"auto"} marginRight={"0"}>Idea: {ideaTitle}</Text>
                </Flex>
                <Textarea id={"reqMessage"} alignSelf={"center"} width={"90%"} placeholder={"Add a collaboration invitation message..."}></Textarea>
                <Flex padding={1} width={"80%"} alignSelf={"center"}>
                    <Text>Additional Contact :</Text>
                    <Input id={"addContact"} placeholder="Additional Contact..." type="text" />
                </Flex>
                <Button
                    rightIcon={<BsSend />}
                    width={"fit-content"}
                    fontSize={"auto"}
                    alignSelf={"center"}
                    isLoading={buttonLoading}
                    loadingText="Sending..."
                    onClick={handleSend}
                >
                    Send Request
                </Button>
                <br></br>
            </Stack>
        </Box>
    );
}