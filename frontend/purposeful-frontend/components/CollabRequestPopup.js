import React from 'react';
import {Box, Text, Button, Stack, Flex, IconButton, Textarea} from '@chakra-ui/react';
import { RxCross1} from "react-icons/rx";


export default function CollabRequestPopup({RequesterMail,ideaID,ideaTitle,removeFunc}){
    var reqMessage = "";
    return (
        <Stack width={"40%"} alignSelf={"center"} marginTop={"10%"} position={"absolute"} borderRadius={"10px"} backgroundColor={"rgba(20,20,20,0.05)"}>
            <Box width={"100%"}>
                <Flex width={"100%"} backgroundColor={"rgba(10,10,10,0.1)"} borderRadius={"10px"}>
                <Text marginLeft={"10px"}>Collaboration Request</Text>
                <IconButton
                marginRight={"0"}
                marginLeft={"auto"}
                size={'sm'}
                icon={<RxCross1/>}
                />
                </Flex>
            </Box>
            <Hstack>
                <Text>User Email: {RequesterMail}</Text>
                <Text>Idea: {ideaTitle}</Text>
            </Hstack>
            <Textarea alignSelf={"center"} width={"90%"} placeholder={"Add a custom collaboration message..."}>{reqMessage}</Textarea>
            <Button
            bg={"blue.400"}
            color={"white"}
            _hover={{
              bg: "blue.500",
            }}
            width={"fit-content"}
            fontSize={"auto"}
            alignSelf={"center"}
            iconRight={<RxCross1/>}
            onClick = {function(){
                
            }}
            >
                Send Collab Request
            </Button>
        </Stack>
    );
}