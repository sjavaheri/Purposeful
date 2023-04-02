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
        <Box zIndex={"10"} backgroundColor={col} left={"50%"} transform={"translate(-50%, 80%)"} borderColor={"rgba(0,0,0,0.1)"} width={"4xl"} alignSelf={"center"} position={"fixed"} borderRadius={"10px"} >
            <Stack>
                <Box width={"100%"}>
                    <Flex width={"100%"} backgroundColor={bannercol} borderRadius={"10px"}>
                        <Text margin={"10px"} fontWeight={"500"} >Collaboration Request</Text>
                        <IconButton
                            margin={"10px"}
                            marginLeft={"auto"}
                            size={'xs'}
                            icon={<RxCross1 />}
                            onClick={() => removeFunc()}
                        />
                    </Flex>
                </Box>
                <Flex width={"60%"} alignSelf={"center"}>
                    <Text marginLeft={"0"} marginRight={"auto"} fontWeight={"500"}>User Email
                        <Text marginLeft={"0"} marginRight={"0"} fontWeight={"100"}>{RequesterMail}</Text>
                    </Text>
                    <Text marginLeft={"auto"} marginRight={"0"} fontWeight={"500"}>Idea
                        <Text marginLeft={"0"} marginRight={"0"} fontWeight={"100"}>{ideaTitle}</Text>
                    </Text>
                </Flex>
                <Flex padding={1} width={"90%"} alignSelf={"center"}>
                    <Text fontWeight={"500"}>Invitation Message</Text>
                    <Textarea id={"reqMessage"} alignSelf={"center"} width={"100%"} placeholder={"Add a collaboration invitation message..."}></Textarea>
                </Flex>
                <Flex padding={1} width={"90%"} alignSelf={"center"}>
                    <Text fontWeight={"500"}>Additional Contact</Text>
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
        </Box >
    );
}