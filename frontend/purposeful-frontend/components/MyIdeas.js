import { Fragment, useState } from "react";
import {
  Box,
  Stack,
  SimpleGrid,
  Button,
  FormErrorMessage,
  Select,
  Input,
  Text,
  Flex,
  Image,
  Tag,
  TagLabel, 
  IconButton,
  Accordion,
  AccordionItem,
  AccordionButton,
  AccordionPanel,
  AccordionIcon,
} from "@chakra-ui/react";

import { useColorModeValue } from "@chakra-ui/color-mode";
import { getMyIdeas } from "@/utils/idea_tool";
import {
    EditIcon,
    SearchIcon
  } from "@chakra-ui/icons";

var field_name = "allIdeas";

export default function MyIdeas() {
    

    const tagColor = useColorModeValue("blue.400", "gray.900");
    const boxColor = useColorModeValue("gray.50", "gray.800");
    const editColor = useColorModeValue("gray.50", "gray.800");
    const searchColor = useColorModeValue("gray.50", "gray.800");
    const searchIconColor = useColorModeValue("gray.20", "gray.750");


    var orig_ideas = [];
    var ideas = [];

    (async () => {
        orig_ideas = await getMyIdeas();
        orig_ideas.map(MakeOption);
        
      })()

    function MakeOption(idea) {
        var idea_topics = [];
        for (let i = 0; i < idea.topics.length; i++) {
            idea_topics.push(idea.topics[i].name)
        }
        var idea = {
            purpose: idea.purpose,
            title: idea.title,
            imageUrl: idea.iconUrl.url,
            topics: idea_topics,
        };
        ideas.push(idea);
    };


    function TagList({ tags }) {
        return (
        <>
            {tags.map((tag, index) => (
            <Tag
                key={index}
                size="md"
                borderRadius="full"
                variant="solid"
                bg={tagColor}
                mr={2}
            >
                <TagLabel>{tag}</TagLabel>
            </Tag>
            ))}
        </>
        );
    }

    function IdeaBoxes({ list }) {
        
        console.log(document.getElementById('my-list'));
        return (
            <SimpleGrid columns={4} spacing={7}>
            {list.map((item, index) => (
                <Box
                key={index}
                rounded={"lg"}
                bg={boxColor}
                boxShadow={"lg"}
                borderWidth="1px" 
                borderRadius="lg" 
                overflow="hidden" 
                p={4}
                m={4}>
                <Flex alignItems="center" justifyContent="space-between">
                <Text mt={4} fontWeight="bold">
                    {item.title}
                </Text>
                <Button size="sm">
                    <EditIcon w={4} h={4} bg={editColor} />
                </Button>
                </Flex>
                <Text mt={2}>{item.purpose}</Text>
                <br></br>
                <Image src={item.imageUrl} height="100px" alt="Example Img"/>
                <br></br>
                <TagList tags={item.topics}></TagList>
                </Box>
                
            ))}
            </SimpleGrid>
        )
        }
    
    //TODO: Connect this function to the backend and get list of ideas
    // function IdeaList({ideaList}){
    //     var ideas = []
    //     for (let i = 0; i < ideaList; i++) {
    //         const idea = ideaList[i];
    //         const title = "Placeholder #" + String.valueOf(i);
    //         ideas.push({title: title, purpose: "Description of idea and data"});
    //     }
    //     return (ideas);
    // }

  return (
    <Stack minWidth="1100px" spacing={8} mx={"auto"} maxW={"2xl"} py={12} px={6}>
        <Flex alignItems="center">
            <Input 
            placeholder="Search..."
            rounded={"lg"}
            bg={searchColor}
            color={"white"}
            boxShadow={"lg"}
            borderWidth="1px" 
            borderRadius="lg" 
            overflow="hidden" 
            p={4}
            m={4}>
            </Input>
            <Button size="md">
                <SearchIcon w={4} h={4} bg={searchIconColor} />
            </Button>
            </Flex>
        
        <IdeaBoxes list={ideas}/>
    </Stack>
  );
}