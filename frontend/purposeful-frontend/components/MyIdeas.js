import { Fragment, useState, useEffect } from "react";
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
  Spinner
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


    const [ideas, setIdeas] = useState([]);
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
      const fetchData = async () => {
        const orig_ideas = await getMyIdeas();
        const ideas = orig_ideas.map(MakeIdeaObj);
        setIdeas(ideas);
        setIsLoading(false);
      };
      fetchData();
    }, []);
  
    function MakeIdeaObj(idea) {
      const idea_topics = idea.topics.map((topic) => topic.name);
      const idea_obj = {
        purpose: idea.purpose,
        title: idea.title,
        imageUrl: idea.iconUrl.url,
        topics: idea_topics,
      };
      return idea_obj;
    }
  
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
              m={4}
            >
              <Flex alignItems="center" justifyContent="space-between">
                <Text mt={4} fontWeight="bold">
                  {item.title}
                </Text>
                <Button size="sm">
                  <EditIcon w={4} h={4} bg={editColor} />
                </Button>
              </Flex>
              <Text mt={2}>{item.purpose}</Text>
              <br />
              <Image src={item.imageUrl} height="100px" alt="Example Img" />
              <br />
              <TagList tags={item.topics}></TagList>
            </Box>
          ))}
        </SimpleGrid>
      );
    }
  
    return (
      <Stack
        minWidth="1100px"
        spacing={8}
        mx={"auto"}
        maxW={"2xl"}
        py={12}
        px={6}
      >
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
            m={4}
          ></Input>
          <Button size="md">
            <SearchIcon w={4} h={4} bg={searchIconColor} />
          </Button>
        </Flex>
  
        {isLoading ? (
        <Spinner />
        ) : (
            ideas.length > 0 ? (
            <IdeaBoxes list={ideas} />
            ) : (
            <p>No ideas found</p>
            )
        )}
      </Stack>
    );
  }