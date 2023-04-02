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
import IdeaFilter2 from "@/components/IdeaFilter2";

// TODO: Style the page for consistency between cards and more details modal
// TODO: Maybe problems with images coming from other sources, allow domain in
// the file next.config.js
// TODO: Add a spinner when the page is loading
// TODO: Change the idea filter to use the new components

// Home page
// Shows a list of ideas
// User can filter ideas by domains, topics, and technologies
// Clicking on an idea will show a modal with more details
export default function page() {
  // Replace with [] when real ideas are available
  const [ideas, setIdeas] = useState([]);

  // We could have a loading component to be used throughout the app
  // if (ideas.length === 0) return <Spinner />;

  return (
    <Box className={styles.container}>
      <Box
        position="fixed"
        // width={"20%"}
        width={"20%"}
        display={"flex"}
        className={styles.ideaFilter}>
        {/* This is the components to update eventually. */}
        {/* The components updates the list of ideas. */}
        {/* <IdeaFilter setIdeas={setIdeas} /> */}
        <IdeaFilter2 setIdeas={setIdeas} />
      </Box>
      <Stack className={styles.ideas}>
        <List>
          {ideas.map((idea, index) => (
            <ListItem
              key={index}
              width={"100%"}
              display={"flex"}
              justifyContent={"center"}
              margin={"50px 0px"}>
              <Box className={styles.ideaCard}>
                {/* Map the ideas into individual cards */}
                {/* Remove the hardcoded objects once real ideas are in the list */}
                <IdeaCard idea={idea} />
              </Box>
            </ListItem>
          ))}
        </List>
      </Stack>
    </Box>
  );
}
