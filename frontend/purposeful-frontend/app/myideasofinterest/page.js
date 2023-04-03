// By default, server components
// Since we want interaction with the user,
// we need to use client components
// see https://beta.nextjs.org/docs/rendering/server-and-client-components#when-to-use-server-vs-client-components
"use client";
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
  Spinner,
  Heading,
  Link,
  useColorMode,
  useColorModeValue,
  Modal,
  ModalOverlay,
  ModalContent,
  ModalBody,
  useDisclosure,
} from "@chakra-ui/react";

import { SearchIcon } from "@chakra-ui/icons";
import CollaborationResponse from "@/components/CollaborationResponse";
import fetchWrapper from "@/utils/fetch_wrapper";
import MoreDetailsOfIdea from "@/components/MoreDetailsOfIdea";

export default function MyIdeasPage() {
  const { colorMode, toggleColorMode } = useColorMode(); // TODO: Move the light/dark mode toggle button to the navigation header
  const { isOpen, onOpen, onClose } = useDisclosure();
  const [hasReacted, setHasReacted] = useState(null);

  var boxClicked = 0;
  var modifyClicked = 0;

  const tagColor = useColorModeValue("blue.400", "gray.900");
  const boxColor = useColorModeValue("gray.50", "gray.800");
  const editColor = useColorModeValue("yellow.300", "gray.700");
  const searchColor = useColorModeValue("gray.50", "gray.800");
  const searchIconColor = useColorModeValue("gray.20", "gray.750");
  const collabRequestColor = useColorModeValue("cyan.100", "cyan.750");

  const [ideas, setIdeas] = useState([]);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const fetchData = async () => {
      const response = await fetchWrapper(`/api/idea/user/requests`);

      const handleData = async (response) => {
        if (!response.ok) {
          return null;
        } else {
          return await response.json();
        }
      };
      const ideas = await handleData(response);
      const ideas_with_topics = ideas.map(MakeIdeaObj);

      setIdeas(ideas_with_topics);
      setIsLoading(false);
    };
    fetchData();
  }, []);

  function MakeIdeaObj(idea) {
    const idea_topics = idea.topics.map((topic) => topic.name);
    console.log(idea)
    const idea_obj = {
      id: idea.id,
      purpose: idea.purpose,
      title: idea.title,
      imageUrl: idea.iconUrl.url,
      topics: idea.topics,
      topic_names: idea_topics,
      domains: idea.domains,
      techs: idea.techs,
      iconUrl: idea.iconUrl,
      imgUrls: idea.imgUrls,
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
      <SimpleGrid columns={3} spacing={7}>
        {list.map((item, index) => (
          <>
            <Box
              rounded={"lg"}
              bg={boxColor}
              boxShadow={"lg"}
              borderWidth="1px"
              borderRadius="lg"
              overflow="hidden"
              p={4}
              m={4}
              cursor="pointer"
              key={index}
              onClick={(event) => {
                // we need to check the tag name so that clicking the view response button doesn't redirect the user
                if (event.target.tagName !== "BUTTON") {
                  onOpen();
                }
              }}
            >
              <Flex alignItems="center" justifyContent="space-between">
                <Text mt={4} fontWeight="bold" fontSize="xl">
                  {item.title}
                </Text>
              </Flex>
              <Text mt={2}>{item.purpose}</Text>
              <br />
              <Image src={item.imageUrl} height="170px" alt="Example Img" />
              <br />
              <TagList tags={item.topic_names}></TagList>
              <Text>
                <br></br>
              </Text>
              <Flex justifyContent="center">
                <CollaborationResponse ideaId={item.id} />
              </Flex>
            </Box>
            <Modal
              isOpen={isOpen}
              onClose={onClose}
              size={"6xl"}
              autoFocus={false}
            >
              <ModalOverlay />
              <ModalContent>
                <MoreDetailsOfIdea
                  idea={item}
                  hasReacted={hasReacted}
                  setHasReacted={setHasReacted}
                />
              </ModalContent>
            </Modal>
          </>
        ))}
      </SimpleGrid>
    );
  }

  return (
    <Flex
      minH={"100vh"}
      align={"center"}
      justify={"center"}
      bg={useColorModeValue("gray.50", "gray.800")}
    >
      <Stack
        minWidth="1100px"
        spacing={8}
        mx={"auto"}
        maxW={"2xl"}
        py={12}
        px={6}
      >
        <Heading textAlign="center" as="h1" size="2xl">
          Pursue a Noble Goal with Others. Your Ideas of Interest!
        </Heading>
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
        ) : ideas.length > 0 ? (
          <IdeaBoxes list={ideas} />
        ) : (
          <Heading as="h3" size="xl">
            No ideas of interest yet!
          </Heading>
        )}
      </Stack>
    </Flex>
  );
}
