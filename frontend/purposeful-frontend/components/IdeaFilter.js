import { Fragment, useEffect, useState } from "react";
import {
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
import { getDomains, getTechs, getTopics } from "@/utils/idea_tool";
import ContainerLabel from "./ContainerLabel";
import { RxPlus } from "react-icons/rx";
import NavBar from "./NavBar";
import { v4 as uuidv4 } from "uuid";
import fetchWrapper from "@/utils/fetch_wrapper";
import notification from "../utils/notification";

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
      domains: [null],
      topics: [null],
      technologies: [null],
    };
    fetchWrapper("/api/idea", null, "POST", payload).then(async (res) => {
      if (res !== null) {
        // User registration failed display error mesages
        notification("error", "An error occurred.", res.errorMessages);
      } else if (res.ok) {
        const ideaList = await res.json();
        setIdeas(ideaList);
      }
    });
  }, []);

  // backend call setIdeas(list)

  async function handleSearchForm(values, actions) {
    if (values.domain === "") {
      values.domain = null;
    }
    if (values.topic === "") {
      values.topic = null;
    }
    if (values.tech === "") {
      values.tech = null;
    }
    const payload = {
      domains: [values.domain],
      topics: [values.topic],
      technologies: [values.tech],
    };

    const response = await fetchWrapper("/api/idea", null, "POST", payload);
    console.log("response", response);
    if (response !== null) {
      // User registration failed display error mesages
      notification("error", "An error occurred.", response.errorMessages);
    } else if (response.ok) {
      const ideaList = await response.json();
      setIdeas(ideaList);
    }
    console.log("domains", values.domain);
    console.log("topics", values.topic);
    console.log("techs", values.tech);

    actions.setSubmitting(false);
  }
  return (
    <Stack width={"100%"}>
      <Box
        //width={"1"}
        rounded={"lg"}
        bg={useColorModeValue("white", "gray.700")}
        boxShadow={"lg"}
        p={0}>
        <Formik
          initialValues={{ domain: "", topic: "", tech: "" }}
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
                        <Select {...field} placeholder="All">
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
                        <Select {...field} placeholder="All">
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
                        <Select {...field} placeholder="All">
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
