"use client";
import Carousel from "@/components/Carousel";
import HighFiveBtn from "@/components/HighFiveBtn";
import CollabRequestPopup from "@/components/CollabRequestPopup";
import {
  Box,
  chakra,
  Container,
  Stack,
  Text,
  Image,
  Flex,
  VStack,
  Button,
  Heading,
  SimpleGrid,
  StackDivider,
  useColorModeValue,
  VisuallyHidden,
  List,
  ListItem,
} from "@chakra-ui/react";
import { BsSend } from "react-icons/bs";
import { useState } from "react";

export default function MoreDetailsOfIdea({ idea }) {
  const [collabReq, set_req] = useState(<Flex display={"none"}></Flex>);

  //TODO: Get requester mail
  function getReqMail() {
    return JSON.parse(localStorage.getItem("appUser")).email;
  }

  function collab_rm() {
    set_req(<Flex display={"none"}></Flex>);
  }

  function collab_req() {
    set_req(
      <CollabRequestPopup
        RequesterMail={getReqMail()}
        ideaID={idea.id}
        ideaTitle={idea.title}
        removeFunc={collab_rm}
      />
    );
  }

  let domains1 = [];
  let domains2 = [];

  let i = 0;
  for (const domain of idea.domains) {
    if (i < idea.domains.length / 2) {
      domains1.push(domain.name);
    } else {
      domains2.push(domain.name);
    }
    i++;
  }

  let topics1 = [];
  let topics2 = [];
  i = 0;
  for (const topic of idea.topics) {
    if (i < idea.topics.length / 2) {
      topics1.push(topic.name);
    } else {
      topics2.push(topic.name);
    }
    i++;
  }

  let techs1 = [];
  let techs2 = [];
  i = 0;
  for (const tech of idea.techs) {
    if (i < idea.techs.length / 2) {
      techs1.push(tech.name);
    } else {
      techs2.push(tech.name);
    }
    i++;
  }

  return (
    <Container maxW={"6xl"}>
      {collabReq}
      <SimpleGrid
        columns={{ base: 1, lg: 2 }}
        spacing={{ base: 8, md: 10 }}
        py={{ base: 18, md: 18 }}>
        <Stack>
          <Box as={"header"}>
            <Heading
              lineHeight={1.1}
              fontWeight={600}
              fontSize={{ base: "2xl", sm: "4xl", lg: "5xl" }}>
              {idea.title}
            </Heading>
            <Text
              color={useColorModeValue("gray.900", "gray.400")}
              fontWeight={300}
              fontSize={"2xl"}>
              {idea.purpose}
            </Text>
          </Box>
          <Image
            rounded={"md"}
            alt={"product image"}
            src={idea.iconUrl.url}
            fit={"cover"}
            align={"center"}
            w={"100%"}
            h={{ base: "100%", sm: "400px", lg: "500px" }}
          />
          <Carousel imgUrls={idea.imgUrls} />
        </Stack>
        <Stack spacing={{ base: 6, md: 10 }}>
          <Stack
            spacing={{ base: 4, sm: 6 }}
            direction={"column"}
            divider={
              <StackDivider
                borderColor={useColorModeValue("gray.200", "gray.600")}
              />
            }>
            <VStack spacing={{ base: 4, sm: 6 }}>
              <Text
                color={useColorModeValue("gray.500", "gray.400")}
                fontSize={"2xl"}
                fontWeight={"300"}>
                Idea Description
              </Text>
              <Text fontSize={"lg"}>{idea.description}</Text>
            </VStack>
            <Box>
              <Text
                fontSize={{ base: "16px", lg: "18px" }}
                color={useColorModeValue("blue.500", "blue.300")}
                fontWeight={"500"}
                textTransform={"uppercase"}
                mb={"4"}>
                Is Paid?
              </Text>

              <SimpleGrid columns={{ base: 1, md: 1 }} spacing={10}>
                <List spacing={2}>
                  <ListItem>{idea.isPaid ? "Yes" : "No"}</ListItem>
                </List>
              </SimpleGrid>
            </Box>
            <Box>
              <Text
                fontSize={{ base: "16px", lg: "18px" }}
                color={useColorModeValue("blue.500", "blue.300")}
                fontWeight={"500"}
                textTransform={"uppercase"}
                mb={"4"}>
                Is In Progress?
              </Text>

              <SimpleGrid columns={{ base: 1, md: 1 }} spacing={10}>
                <List spacing={2}>
                  <ListItem>{idea.inProgress ? "Yes" : "No"}</ListItem>
                </List>
              </SimpleGrid>
            </Box>
            <Box>
              <Text
                fontSize={{ base: "16px", lg: "18px" }}
                color={useColorModeValue("blue.500", "blue.300")}
                fontWeight={"500"}
                textTransform={"uppercase"}
                mb={"4"}>
                Domains
              </Text>

              <SimpleGrid columns={{ base: 1, md: 2 }} spacing={10}>
                <List spacing={2}>
                  {domains1.map((domain, index) => (
                    <ListItem key={index}>{domain}</ListItem>
                  ))}
                </List>
                <List spacing={2}>
                  {domains2.map((domain, index) => (
                    <ListItem key={index}>{domain}</ListItem>
                  ))}
                </List>
              </SimpleGrid>
            </Box>
            <Box>
              <Text
                fontSize={{ base: "16px", lg: "18px" }}
                color={useColorModeValue("blue.500", "blue.300")}
                fontWeight={"500"}
                textTransform={"uppercase"}
                mb={"4"}>
                Topics
              </Text>

              <SimpleGrid columns={{ base: 1, md: 2 }} spacing={10}>
                <List spacing={2}>
                  {topics1.map((topic, value) => (
                    <ListItem key={value}>{topic}</ListItem>
                  ))}
                </List>
                <List spacing={2}>
                  {topics2.map((topic, value) => (
                    <ListItem key={value}>{topic}</ListItem>
                  ))}
                </List>
              </SimpleGrid>
            </Box>
            <Box>
              <Text
                fontSize={{ base: "16px", lg: "18px" }}
                color={useColorModeValue("blue.500", "blue.300")}
                fontWeight={"500"}
                textTransform={"uppercase"}
                mb={"4"}>
                Technologies
              </Text>

              <SimpleGrid columns={{ base: 1, md: 2 }} spacing={10}>
                <List spacing={2}>
                  {techs1.map((tech, index) => (
                    <ListItem key={index}>{tech}</ListItem>
                  ))}
                </List>
                <List spacing={2}>
                  {techs2.map((tech, index) => (
                    <ListItem key={index}>{tech}</ListItem>
                  ))}
                </List>
              </SimpleGrid>
            </Box>
          </Stack>
          <SimpleGrid columns={{ base: 1, md: 2 }} spacing={10}>
            <HighFiveBtn idea_id={idea.id} />
            <Button rightIcon={<BsSend />} onClick={() => collab_req()}>
              Send Collaboration Request
            </Button>
          </SimpleGrid>
        </Stack>
      </SimpleGrid>
    </Container>
  );
}
