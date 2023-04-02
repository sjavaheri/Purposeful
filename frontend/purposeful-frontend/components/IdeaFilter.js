import { useEffect, useState } from "react";
import {
  Box,
  FormControl,
  FormLabel,
  Stack,
  Button,
  useColorModeValue,
  Select,
} from "@chakra-ui/react";
import { Field, Form, Formik } from "formik";
import { getDomains, getTechs, getTopics } from "@/utils/idea_tool";
import fetchWrapper from "@/utils/fetch_wrapper";
import notification from "../utils/notification";

// First version of the idea filter that only allows for one selection
export default function IdeaFilter({ setIdeas }) {
  const [domains, setDomains] = useState([]);
  const [topics, setTopics] = useState([]);
  const [techs, setTechs] = useState([]);

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
      domains: null,
      topics: null,
      technologies: null,
    };
    fetchWrapper("/api/idea", null, "POST", payload).then(async (res) => {
      if (!res.ok) {
        notification("error", "An error occurred.", res.errorMessages);
      } else {
        let ideaList = await res.json();
        // Updates the list of ideas in the parent component
        setIdeas(ideaList);
      }
    });
  }, []);

  // Handle the search form submission
  async function handleSearchForm(values, actions) {
    const payload = {
      domains: values.domain === "All" ? null : [values.domain],
      topics: values.topic === "All" ? null : [values.topic],
      technologies: values.tech === "All" ? null : [values.tech],
    };
    fetchWrapper("/api/idea", null, "POST", payload).then(async (res) => {
      if (!res.ok) {
        notification("error", "An error occurred.", res.errorMessages);
      } else {
        let ideaList = await res.json();
        // Updates the list of ideas in the parent component
        setIdeas(ideaList);
      }
    });
    actions.setSubmitting(false);
  }

  return (
    <Stack width={"100%"}>
      <Box
        id="idea-filter"
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
