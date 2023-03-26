import { Fragment, useState } from "react";
import {
  Box,
  FormControl,
  FormLabel,
  Input,
  Stack,
  HStack,
  Checkbox,
  Textarea,
  Button,
  useColorModeValue,
  FormErrorMessage,
  Select,
  IconButton,
  Accordion,
  AccordionItem,
  AccordionButton,
  AccordionPanel,
  AccordionIcon,
} from "@chakra-ui/react";

import {
    DeleteIcon
  } from "@chakra-ui/icons";
import { Field, Form, Formik } from "formik";
import { RxPlus } from "react-icons/rx";


export default function MyIdeas() {

    function AccordionList({ data }) {
        return (
            <Accordion>
            {data.map((item, index) => (
                <AccordionItem key={index}>
                <h2>
                    <AccordionButton>
                    {item.title}
                    </AccordionButton>
                </h2>
                <AccordionPanel>
                    {item.content}
                </AccordionPanel>
                </AccordionItem>
            ))}
            </Accordion>
        );
        }
    
    function IdeaList({ideaList}){
        var ideas = []
        for (let i = 0; i < ideaList; i++) {
            const idea = ideaList[i];
            ideas.push({title: idea.title, content: idea.description});
        }
        <AccordionList data={[
            { title: "Item 1", content: "Lorem ipsum dolor sit amet." },
            { title: "Item 2", content: "Consectetur adipiscing elit." },
            { title: "Item 3", content: "Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua." }
        ]} 
        />
    }
  (async () => {
    var hello = []
  })()

  return (
    <AccordionList data={[
    { title: "Item 1", content: "Lorem ipsum dolor sit amet." },
    { title: "Item 2", content: "Consectetur adipiscing elit." },
    { title: "Item 3", content: "Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua." }
    ]} />
  );
}