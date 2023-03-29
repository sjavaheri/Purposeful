import React from 'react';
import {Input, Box, Text, Button, Stack, Flex, IconButton, Textarea} from '@chakra-ui/react';
import { RxCross1} from "react-icons/rx";
import { useColorModeValue } from '@chakra-ui/react';
import { BsSend } from "react-icons/bs";

export default function CollabRequestPopup({RequesterMail,ideaID,ideaTitle,removeFunc}){
    const col = useColorModeValue("rgb(255,255,255)", "rgb(26,32,44)")
    return (
        <Box zIndex={"10"} backgroundColor={col} marginTop={"5%"} marginLeft={"20%"} borderColor={"rgba(0,0,0,0.1)"} border={"solid"} width={"40%"} alignSelf={"center"} position={"fixed"} borderRadius={"10px"}>
        <Stack>
            <Box width={"100%"}>
                <Flex width={"100%"} backgroundColor={"rgba(10,10,10,0.2)"} borderRadius={"10px"} borderBottom={"solid"}>
                <Text marginLeft={"10px"}>Collaboration Request</Text>
                <IconButton
                borderLeft={"solid"}
                borderRight={"solid"}
                marginRight={"0"}
                marginLeft={"auto"}
                size={'sm'}
                icon={<RxCross1/>}
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
                <Input id={"addContact"} placeholder="Additional Contact..." type="text"/>
            </Flex>
            <Button
            rightIcon={<BsSend/>}
            width={"fit-content"}
            fontSize={"auto"}
            alignSelf={"center"}
            onClick = {function(){
                //Connect to backend here
            }}
            >
                Send Request
            </Button>
            <br></br>
        </Stack>
        </Box>
    );
}