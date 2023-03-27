import React from 'react';
import ReactDOM from 'react-dom';
import {Box, HStack, IconButton} from '@chakra-ui/react';
import { RxCross1} from "react-icons/rx";

export default function ContainerLabel({innerTxt, arr, arr2, refresh}){
    function find_component_in_arr(c_arr){
        for(var i = 0; i < c_arr.length; i++){
            if((c_arr[i].name) === innerTxt){
                return i;
            }
        }
        return -1;
    }
    return (
        <HStack fontSize={"small"} paddingLeft={2} borderRadius={"10px"} borderStyle={"solid"} border={"solid"} borderColor={"rgba(255,255,255,0.6)"} width={"fit-content"} backgroundColor={"rgba(0,0,0,0.2)"}>
        <span>{innerTxt}</span>
        <IconButton
            size='sm'
            icon={<RxCross1/>}
            onClick = {function(){
                var indexw = find_component_in_arr(arr);
                arr.splice(indexw,1);
                arr2.splice(indexw,1);
                refresh();
                //ReactDOM.unmountComponentAtNode(ReactDOM.findDOMNode(this).parentNode);
            }}
        />
        </HStack>
    );
}