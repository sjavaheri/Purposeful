import { useState, useEffect } from "react";
import {
  Box,
  Button,
  Flex,
  Heading,
  Image,
  Input,
  Modal,
  ModalOverlay,
  ModalContent,
  SimpleGrid,
  Stack,
  Text,
  Tag,
  TagLabel,
  Spinner,
  useColorModeValue,
  useDisclosure,
} from "@chakra-ui/react";
import { getMyIdeas } from "@/utils/idea_tool";
import { EditIcon, SearchIcon } from "@chakra-ui/icons";
import MoreDetailsOfIdea from "./MoreDetailsOfIdea";

export default function MyIdeas() {
  const tagColor = useColorModeValue("blue.400", "blue.700");
  const boxColor = useColorModeValue("gray.50", "gray.800");
  const editColor = useColorModeValue("yellow.300", "yellow.900");
  const searchColor = useColorModeValue("gray.50", "gray.800");
  const searchIconColor = useColorModeValue("gray.20", "gray.750");
  const collabRequestColor = useColorModeValue("cyan.100", "cyan.700");

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
      id: idea.id,
      purpose: idea.purpose,
      title: idea.title,
      description: idea.description,
      imageUrl: idea.iconUrl.url,
      topics: idea.topics,
      topic_names: idea_topics,
      domains: idea.domains,
      techs: idea.techs,
      iconUrl: idea.iconUrl,
      imgUrls: idea.imgUrls,
      inProgress: idea.inProgress,
      isPaid: idea.isPaid,
      isPrivate: idea.isPrivate,
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

  function IdeaBoxes({ item, index }) {
    const { isOpen, onOpen, onClose } = useDisclosure();
    const [hasReacted, setHasReacted] = useState(null);

    return (
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
          onClick={() => {
            onOpen();
          }}
        >
          <Flex alignItems="center" justifyContent="space-between">
            <Text mt={4} fontWeight="bold" fontSize="xl">
              {item.title}
            </Text>
            <Button
              size="sm"
              bg={editColor}
              hoverBg={editColor}
              _hover={{ boxShadow: "none" }}
              onClick={(event) => {
                event.stopPropagation();
                window.location.href = "/idea/modify?ideaId=" + item.id;
              }}
            >
              <EditIcon w={4} h={4} bg={editColor} />
            </Button>
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
            <Button
              size="sm"
              bg={collabRequestColor}
              hoverBg={collabRequestColor}
              _hover={{ boxShadow: "none" }}
              onClick={(event) => {
                event.stopPropagation();
                window.location.href =
                  "/collaborationRequests?ideaId=" + item.id;
              }}
            >
              View Collaboration Requests
            </Button>
          </Flex>
        </Box>

        <Modal isOpen={isOpen} onClose={onClose} size={"6xl"} autoFocus={false}>
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
      <Heading textAlign="center" as="h1" size="2xl">
        The hub of creativity. Your ideas!
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
        <SimpleGrid columns={3} spacing={7}>
          {ideas.map((item, index) => (
            <IdeaBoxes item={item} index={index} />
          ))}
        </SimpleGrid>
      ) : (
        <Heading as="h3" size="xl">
          No ideas yet!
        </Heading>
      )}
    </Stack>
  );
}
