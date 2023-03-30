"use client";

import Image from "next/image";
import NextLink from "next/link";
import {
  Box,
  Heading,
  Text,
  Stack,
  Avatar,
  useColorModeValue,
  Icon,
  Link,
  Popover,
  PopoverTrigger,
  PopoverContent,
  PopoverBody,
  PopoverArrow,
  PopoverHeader,
} from "@chakra-ui/react";
import { GiReceiveMoney } from "react-icons/gi";
import { useState } from "react";
import MoreDetailsOfIdea from "./MoreDetailsOfIdea";

export default function IdeaCard({ idea }) {
  const [isPopoverOpen, setIsPopoverOpen] = useState(false);

  function handlePopoverOpen() {
    setIsPopoverOpen(true);
  }

  function handlePopoverClose() {
    setIsPopoverOpen(false);
  }

  return (
    // <Link as={NextLink} href={`/idea/${idea.id}`}>
    <Popover isOpen={isPopoverOpen} onClose={handlePopoverClose}>
      <PopoverTrigger>
        <Box
          maxW={"100%"}
          w={"full"}
          bg={useColorModeValue("white", "gray.900")}
          boxShadow={"2xl"}
          rounded={"md"}
          p={6}
          overflow={"hidden"}
          onClick={handlePopoverOpen}>
          <Box
            h={"400px"}
            bg={"gray.100"}
            mt={-6}
            mx={-6}
            mb={6}
            pos={"relative"}>
            <Image
              src={
                "https://images.unsplash.com/photo-1515378791036-0648a3ef77b2?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80"
              }
              fill
              style={{ objectFit: "cover" }}
              zIndex={0}
            />
          </Box>
          <Stack paddingTop={"5%"}>
            <Heading
              color={useColorModeValue("gray.700", "white")}
              fontSize={"2xl"}
              fontFamily={"body"}>
              {idea.title}
            </Heading>
            <Text color={"gray.500"}>{idea.purpose}</Text>
            <Text
              color={"green.500"}
              textTransform={"uppercase"}
              fontWeight={800}
              fontSize={"sm"}
              letterSpacing={1.1}>
              Domains: {idea.domains.map((domain) => domain.name).join(", ")}
            </Text>
            <Text
              color={"green.500"}
              textTransform={"uppercase"}
              fontWeight={800}
              fontSize={"sm"}
              letterSpacing={1.1}>
              Topics: {idea.topics.map((topic) => topic.name).join(", ")}
            </Text>
            <Text
              color={"green.500"}
              textTransform={"uppercase"}
              fontWeight={800}
              fontSize={"sm"}
              letterSpacing={1.1}>
              Technologies: {idea.techs.map((tech) => tech.name).join(", ")}
            </Text>
            <Text
              color={"green.500"}
              textTransform={"uppercase"}
              fontWeight={800}
              fontSize={"sm"}
              letterSpacing={1.1}>
              <Icon as={GiReceiveMoney} boxSize={6} />{" "}
              {idea.isPaid ? "Paid" : "Unpaid"}
            </Text>
          </Stack>
        </Box>
      </PopoverTrigger>
      <PopoverContent size={"full"}>
        <Box maxW={"100%"}>
          <MoreDetailsOfIdea idea={idea} />
        </Box>
      </PopoverContent>
    </Popover>

    // </Link>
  );
}
