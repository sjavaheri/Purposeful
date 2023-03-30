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
import { useState, useEffect, Fragment } from "react";

var fullfilled = 0;
var field_name = "domains";
var c_domains = [];
var c_topics = [];
var c_techs = [];

var domains_sel = <Select id="domains"></Select>;
var topics_sel = <Select id="topics"></Select>;
var techs_sel = <Select id="techs"></Select>;

var rendered_domains = [];
var rendered_topics = [];
var rendered_techs = [];

export default function page() {
  // Search parameters
  // const [domains, setDomains] = useState([]);
  // const [topics, setTopics] = useState([]);
  // const [technologies, setTechnologies] = useState([]);
  // List of ideas
  const [ideas, setIdeas] = useState([1, 2, 3, 4, 5, 6, 7, 8, 9, 10]);
  // useEffect(() => {
  //   verifyToken().then((res) => {
  //     setVerified(res);
  //     setAppUser(localStorage.getItem("appUser"));
  //     setGrantedAuth(getAuthorities());
  //   });
  // }, []);

  // if (ideas.length === 0) return <Spinner />;
  const [render_domains, set_rd] = useState(<Fragment></Fragment>);
  const [render_topics, set_tp] = useState(<Fragment></Fragment>);
  const [render_techs, set_tc] = useState(<Fragment></Fragment>);

  var domainContainer = (
    <Stack
      on
      direction={["column", "row"]}
      id={"domainContainer"}
      wrap={"wrap"}>
      {render_domains}
    </Stack>
  );
  var topicContainer = (
    <Stack on direction={["column", "row"]} id={"topicContainer"} wrap={"wrap"}>
      {render_topics}
    </Stack>
  );
  var techContainer = (
    <Stack on direction={["column", "row"]} id={"techContainer"} wrap={"wrap"}>
      {render_techs}
    </Stack>
  );
  var domains = [];
  var topics = [];
  var techs = [];

  (async () => {
    domains = await getDomains();
    topics = await getTopics();
    techs = await getTechs();
    if (fullfilled == 0) {
      fullfilled++;
      domains.map(MakeOption);
    }
    if (fullfilled == 1) {
      fullfilled++;
      field_name = "topics";
      topics.map(MakeOption);
    }
    if (fullfilled == 2) {
      fullfilled++;
      field_name = "techs";
      techs.map(MakeOption);
    }
  })();

  var refreshfn = function () {
    set_rd(<Fragment>{rendered_domains.concat([])}</Fragment>);
    set_tp(<Fragment>{rendered_topics.concat([])}</Fragment>);
    set_tc(<Fragment>{rendered_techs.concat([])}</Fragment>);
  };
  function MakeOption(X) {
    const el = document.createElement("option");
    el.setAttribute("value", X.id);
    el.textContent = X.name;
    document.getElementById(field_name).appendChild(el);
  }

  function PushObj(selectedIndex, arr, c_arr, arr2) {
    if (find_in_arr(selectedIndex, arr, c_arr) == -1) {
      const el = (
        <ContainerLabel
          key={arr[selectedIndex].name}
          innerTxt={arr[selectedIndex].name}
          arr={c_arr}
          arr2={arr2}
          refresh={refreshfn}
        />
      );
      c_arr.push(arr[selectedIndex]);
      arr2.push(el);
      refreshfn();
    }
  }
  return (
    <Stack width={"100%"}>
      <Box
        rounded={"lg"}
        bg={useColorModeValue("white", "gray.700")}
        boxShadow={"lg"}
        p={2}
        minHeight={"10%"}>
        <Formik
          initialValues={{
            title: "",
            purpose: "",
            description: "",
          }}
          onSubmit={(values, actions) => {
            setTimeout(async () => {
              console.log(values); // TODO: To be removed once the API is connected
              // TODO: Set the error messages for the fields according to the API response
              // TODO: Differentiate API methods depending on authentication status
              actions.setSubmitting(false);
              // TODO: Redirect to the login page
            }, 1000);
          }}>
          {(props) => (
            <Form>
              <HStack spacing={2}>
                <Box width={"100%"} display={"flex"}>
                  <Field name="Domain">
                    {({ field, form }) => (
                      <FormControl id="domain">
                        <FormLabel>Domains</FormLabel>
                        <HStack placeholder="First name">{domains_sel}</HStack>
                      </FormControl>
                    )}
                  </Field>
                  {domainContainer}
                </Box>
                <Box width={"100%"} display={"flex"}>
                  <Field name="Topic">
                    {({ field, form }) => (
                      <FormControl
                        id="topic"
                        isInvalid={form.errors.topic && form.touched.topic}>
                        <FormLabel>Topics</FormLabel>
                        <HStack>{topics_sel}</HStack>
                      </FormControl>
                    )}
                  </Field>
                  {topicContainer}
                </Box>
                <Box width={"100%"} display={"flex"}>
                  <Field name="Tech">
                    {({ field, form }) => (
                      <FormControl
                        id="tech"
                        isInvalid={form.errors.tech && form.touched.tech}>
                        <FormLabel>Technologies</FormLabel>
                        <HStack>{techs_sel}</HStack>
                      </FormControl>
                    )}
                  </Field>
                  {techContainer}
                </Box>
                {/* <Box width={"100%"} display={"flex"}>
                  <Button
                    width={"100%"}
                    bg={"blue.400"}
                    color={"white"}
                    _hover={{
                      bg: "blue.500",
                    }}
                    type="submit">
                    Search
                  </Button>
                </Box> */}
              </HStack>
            </Form>
          )}
        </Formik>
      </Box>
      <List minWidth="100%">
        {ideas.map((idea) => (
          <ListItem>
            <IdeaCard key={idea} />
          </ListItem>
        ))}
      </List>
    </Stack>
  );
}
