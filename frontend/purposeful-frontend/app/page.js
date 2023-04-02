"use client";
import { List, ListItem, Box, Stack } from "@chakra-ui/react";
import IdeaCard from "@/components/IdeaCard";
import { useState } from "react";
import styles from "./styles.module.css";
import IdeaFilter2 from "@/components/IdeaFilter2";

// Home page
// Shows a list of ideas
// User can filter ideas by domains, topics, and technologies
// Clicking on an idea will show a modal with more details
export default function page() {
  const [ideas, setIdeas] = useState([]);

  return (
    <Box className={styles.container}>
      <Box
        position="fixed"
        width={"20%"}
        display={"flex"}
        className={styles.ideaFilter}>
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
                <IdeaCard idea={idea} />
              </Box>
            </ListItem>
          ))}
        </List>
      </Stack>
    </Box>
  );
}
