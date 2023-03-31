"use client";

import Image from "next/image";
import {
  Box,
  Heading,
  Text,
  Stack,
  Avatar,
  useColorModeValue,
  useDisclosure,
  Icon,
  Modal,
  ModalOverlay,
  ModalContent,
  ModalBody,
} from "@chakra-ui/react";
import { GiReceiveMoney } from "react-icons/gi";
import MoreDetailsOfIdea from "./MoreDetailsOfIdea";
import styles from "../app/styles.module.css";

export default function IdeaCard({ idea }) {
  const { isOpen, onOpen, onClose } = useDisclosure();

  return (
    <>
      <Box
        maxW={"100%"}
        w={"full"}
        bg={useColorModeValue("white", "gray.900")}
        boxShadow={"2xl"}
        rounded={"md"}
        p={6}
        overflow={"hidden"}
        onClick={onOpen}>
        <Box
          h={"400px"}
          bg={"gray.100"}
          mt={-6}
          mx={-6}
          mb={6}
          pos={"relative"}>
          <Image src={idea.iconUrl.url} fill style={{ objectFit: "cover" }} />
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
            color={"blue.500"}
            textTransform={"uppercase"}
            fontWeight={800}
            fontSize={"sm"}
            letterSpacing={1.1}>
            Domains: {idea.domains.map((domain) => domain.name).join(", ")}
          </Text>
          <Text
            color={"blue.500"}
            textTransform={"uppercase"}
            fontWeight={800}
            fontSize={"sm"}
            letterSpacing={1.1}>
            Topics: {idea.topics.map((topic) => topic.name).join(", ")}
          </Text>
          <Text
            color={"blue.500"}
            textTransform={"uppercase"}
            fontWeight={800}
            fontSize={"sm"}
            letterSpacing={1.1}>
            Technologies: {idea.techs.map((tech) => tech.name).join(", ")}
          </Text>
          <Text
            color={"blue.500"}
            textTransform={"uppercase"}
            fontWeight={800}
            fontSize={"sm"}
            letterSpacing={1.1}>
            <Icon as={GiReceiveMoney} boxSize={6} />{" "}
            {idea.isPaid ? "Paid" : "Unpaid"}
          </Text>
        </Stack>
      </Box>
      <Modal isOpen={isOpen} onClose={onClose} size={"6xl"} autoFocus={false}>
        <ModalOverlay />
        <ModalContent>
          <MoreDetailsOfIdea idea={idea} />
        </ModalContent>
      </Modal>
    </>
  );
}
