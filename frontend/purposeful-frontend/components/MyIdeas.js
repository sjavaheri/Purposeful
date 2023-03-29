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
import {
    EditIcon,
    SearchIcon
  } from "@chakra-ui/icons";


export default function MyIdeas() {

    //KEEPING THIS IN CASE WE WANT TO CHANGE DESIGN IN WEEK 3
    // function AccordionList({ data }) {
    //     return (
    //         <Accordion>
    //         {data.map((item, index) => (
    //             <AccordionItem key={index}>
    //             <h2>
    //                 <Flex alignItems="center">
    //                 <AccordionButton>
    //                     {item.title}
    //                 </AccordionButton>
    //                 <Button size="sm">
    //                     <EditIcon w={4} h={4} color="gray.700" />
    //                 </Button>
    //                 </Flex>
    //             </h2>
    //             <AccordionPanel>
    //                 {item.content}
    //             </AccordionPanel>
    //             </AccordionItem>
    //         ))}
    //         </Accordion>
    //     );
    //     }
    const tagColor = useColorModeValue("blue.400", "gray.900");
    const boxColor = useColorModeValue("gray.50", "gray.800");
    const editColor = useColorModeValue("gray.50", "gray.800");
    const searchColor = useColorModeValue("gray.50", "gray.800");
    const searchIconColor = useColorModeValue("gray.20", "gray.750");
    
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
        <IdeaBoxes list={[
            {
                title: "Idea 1",
                purpose: "This is the purpose for Idea 1",
                imageUrl: "https://images.pexels.com/photos/8145352/pexels-photo-8145352.jpeg",
                topics: ["topic 1", "topic 2"]
            },
            {
                title: "Idea 2",
                purpose: "This is the purpose for Idea 2",
                imageUrl: "https://images.pexels.com/photos/7413915/pexels-photo-7413915.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2",
                topics: ["topic 1", "topic 2"]
            },
            {
                title: "Idea 3",
                purpose: "This is the purpose for Idea 3",
                imageUrl: "https://images.pexels.com/photos/6238186/pexels-photo-6238186.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2",
                topics: ["topic 1", "topic 2"]
            },
            {
                title: "Idea 4",
                purpose: "This is the purpose for Idea 4",
                imageUrl: "https://images.pexels.com/photos/6476783/pexels-photo-6476783.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2",
                topics: ["topic 1", "topic 2"]
            },
            {
                title: "Idea 5",
                purpose: "This is the purpose for Idea 5",
                imageUrl: "https://images.pexels.com/photos/4344860/pexels-photo-4344860.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2",
                topics: ["topic 1", "topic 2"]
            },
            {
                title: "Idea 6",
                purpose: "This is the purpose for Idea 6",
                imageUrl: "https://images.pexels.com/photos/3727463/pexels-photo-3727463.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2",
                topics: ["topic 1", "topic 2"]
            },
        ]} />
    </Stack>
  );
}