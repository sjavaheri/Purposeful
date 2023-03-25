import React from 'react';
import ReactDOM from 'react-dom';
import {Box, HStack, IconButton} from '@chakra-ui/react';
import { RxCross1} from "react-icons/rx";

var ind = 0;
var innrTxt = "";

export default function ContainerLabel({innerTxt, index, arr}){
    innrTxt = innerTxt;
    ind = index;
    return (
        <HStack paddingLeft={2} borderRadius={"10px"} borderStyle={"solid"} border={"solid"} borderColor={"rgba(255,255,255,0.6)"} width={"fit-content"} backgroundColor={"rgba(0,0,0,0.2)"}>
        <span>{innerTxt}</span>
        <IconButton
            icon={<RxCross1/>}
            onClick = {function(){
                //arr.splice(index,1);
                //ReactDOM.unmountComponentAtNode(ReactDOM.findDOMNode(this).parentNode);
            }}
        />
        </HStack>
    );
}