import { Fragment, useEffect, useState } from "react";
import {
  Box,
  FormControl,
  FormLabel,
  Stack,
  Button,
  useColorModeValue,
  Select,
  Spinner,
} from "@chakra-ui/react";
import { Field, Form, Formik } from "formik";
import { getDomains, getTechs, getTopics } from "@/utils/idea_tool";
import ContainerLabel from "./ContainerLabel";
import { RxPlus } from "react-icons/rx";
import NavBar from "./NavBar";
import { v4 as uuidv4 } from "uuid";
import fetchWrapper from "@/utils/fetch_wrapper";
import notification from "../utils/notification";

// TODO: Modify the idea filter to be able to select multiple domains, topics, and technologies
// At the moment, it only allows for one selection
export default function IdeaFilter({ setIdeas }) {
  const [domains, setDomains] = useState([]);
  const [topics, setTopics] = useState([]);
  const [techs, setTechs] = useState([]);

  // Initially get all domains, topics, and technologies in the system
  // Then get all ideas
  useEffect(() => {
    getDomains().then((res) => {
      setDomains(res);
    });
    getTopics().then((res) => {
      setTopics(res);
    });
    getTechs().then((res) => {
      setTechs(res);
    });
    const payload = {
      domains: [null],
      topics: [null],
      technologies: [null],
    };
    fetchWrapper("/api/idea", null, "POST", payload).then(async (res) => {
      if (res !== null) {
        notification("error", "An error occurred.", res.errorMessages);
      } else if (res.ok) {
        const ideaList = await res.json();
        // SUPER IMPORTANT
        // This is the function that updates the list of ideas in the parent component
        setIdeas(ideaList);
      }
    });
  }, []);

  // Handle the search form submission
  async function handleSearchForm(values, actions) {
    if (values.domain === "All") {
      values.domain = null;
    }
    if (values.topic === "All") {
      values.topic = null;
    }
    if (values.tech === "All") {
      values.tech = null;
    }
    // Takes only one value unfortunately
    const payload = {
      domains: [values.domain],
      topics: [values.topic],
      technologies: [values.tech],
    };
    console.log(payload);

    const response = await fetchWrapper("/api/idea", null, "POST", payload);
    if (response !== null) {
      notification("error", "An error occurred.", response.errorMessages);
    } else if (response.ok) {
      const ideaList = await response.json();
      setIdeas(ideaList);
    }
    actions.setSubmitting(false);
  }
  // Same frontend code as the create idea form for domains, topics, and technologies
  // Remove the old logic and replaced it with Select components
  // Add better logic eventually
  return (
    <Stack width={"100%"}>
      <Box
        //width={"1"}
        rounded={"lg"}
        bg={useColorModeValue("white", "gray.700")}
        boxShadow={"lg"}
        p={0}>
        <Formik
          initialValues={{ domain: "All", topic: "All", tech: "All" }}
          onSubmit={async (values, actions) => {
            await handleSearchForm(values, actions);
          }}>
          {(props) => (
            <Form>
              <Stack spacing={8} width={"100%"} padding={"10%"}>
                <Box>
                  <Field name="domain">
                    {({ field, form }) => (
                      <FormControl>
                        <FormLabel>Domains</FormLabel>
                        <Select {...field}>
                          {domains.map((domain) => (
                            <option key={domain.id} value={domain.name}>
                              {domain.name}
                            </option>
                          ))}
                        </Select>
                      </FormControl>
                    )}
                  </Field>
                </Box>
                <Box>
                  <Field name="topic">
                    {({ field, form }) => (
                      <FormControl
                        id="topic"
                        isInvalid={form.errors.topic && form.touched.topic}>
                        <FormLabel>Topics</FormLabel>
                        <Select {...field}>
                          {topics.map((topic) => (
                            <option key={topic.id} value={topic.name}>
                              {topic.name}
                            </option>
                          ))}
                        </Select>
                      </FormControl>
                    )}
                  </Field>
                </Box>
                <Box>
                  <Field name="tech">
                    {({ field, form }) => (
                      <FormControl
                        id="tech"
                        isInvalid={form.errors.tech && form.touched.tech}>
                        <FormLabel>Technologies</FormLabel>
                        <Select {...field}>
                          {techs.map((tech) => (
                            <option key={tech.id} value={tech.name}>
                              {tech.name}
                            </option>
                          ))}
                        </Select>
                      </FormControl>
                    )}
                  </Field>
                </Box>
                <Box width={"100%"} display={"flex"}>
                  <Button
                    width={"100%"}
                    bg={"blue.400"}
                    color={"white"}
                    _hover={{
                      bg: "blue.500",
                    }}
                    type="submit"
                    isLoading={props.isSubmitting}>
                    Search
                  </Button>
                </Box>
              </Stack>
            </Form>
          )}
        </Formik>
      </Box>
    </Stack>
  );
}
