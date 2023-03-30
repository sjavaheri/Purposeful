"use client";
import {
  Spinner,
  List,
  ListItem,
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
} from "@chakra-ui/react";
import { Field, Form, Formik } from "formik";
import { RxPlus } from "react-icons/rx";
import { getDomains, getTechs, getTopics } from "@/utils/idea_tool";
import ContainerLabel from "../components/ContainerLabel";
import IdeaCard from "@/components/IdeaCard";
import IdeaFilter from "@/components/IdeaFilter";
import { useState, useEffect, Fragment } from "react";
import styles from "./styles.module.css";

export default function page() {
  // Search parameters
  // const [domains, setDomains] = useState([]);
  // const [topics, setTopics] = useState([]);
  // const [technologies, setTechnologies] = useState([]);
  // List of ideas
  const [ideas, setIdeas] = useState([]);
  // useEffect(() => {
  //   verifyToken().then((res) => {
  //     setVerified(res);
  //     setAppUser(localStorage.getItem("appUser"));
  //     setGrantedAuth(getAuthorities());
  //   });
  // }, []);

  // if (ideas.length === 0) return <Spinner />;

  return (
    <Box className={styles.container}>
      <Box className={styles.ideaFilter}>
        <IdeaFilter setIdeas={setIdeas} />
      </Box>
      <Stack className={styles.ideas}>
        <List>
          {ideas.map((idea) => (
            <ListItem
              key={idea}
              width={"100%"}
              display={"flex"}
              justifyContent={"center"}
              margin={"50px 0px"}>
              <Box className={styles.ideaCard}>
                <IdeaCard />
              </Box>
            </ListItem>
          ))}
        </List>
      </Stack>
    </Box>
  );
}
