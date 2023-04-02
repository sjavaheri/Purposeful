import { Fragment, useEffect, useState } from "react";
import {
  Box,
  FormControl,
  FormLabel,
  Stack,
  HStack,
  Button,
  useColorModeValue,
  Select,
  IconButton,
} from "@chakra-ui/react";
import { Field, Form, Formik } from "formik";
import { getDomains, getTechs, getTopics } from "@/utils/idea_tool";
import ContainerLabel from "./ContainerLabel";
import { RxPlus } from "react-icons/rx";
import fetchWrapper from "@/utils/fetch_wrapper";
import notification from "@/utils/notification";

var domains = [];
var topics = [];
var techs = [];

var fullfilled = 0;
var field_name = "domains";
var c_domains = [];
var c_topics = [];
var c_techs = [];

var domains_sel = (
  <Select id="domains">
    <option></option>
  </Select>
);
var topics_sel = (
  <Select id="topics">
    <option></option>
  </Select>
);
var techs_sel = (
  <Select id="techs">
    <option></option>
  </Select>
);

var rendered_domains = [];
var rendered_topics = [];
var rendered_techs = [];

// Second version of the idea filter that allows for multiple selections
export default function IdeaFilter2({ setIdeas }) {
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

  //Form Data
  const [domns, setDomns] = useState([]);
  const [topcs, setTopcs] = useState([]);
  const [tchs, setTchs] = useState([]);
  const [enteredDomains, setEnteredDomains] = useState(false);
  const [enteredTopics, setEnteredTopics] = useState(false);
  const [enteredTechs, setEnteredTechs] = useState(false);

  function handleArrays(arg1, arg2, arg3, arg4, enteredFn) {
    enteredFn(true);
    PushObj(arg1, arg2, arg3, arg4);
  }

  // Handle the search form submission
  async function handleSearchForm(values, actions) {
    const payload = {
      domains: c_domains.length === 0 ? null : getNames(c_domains),
      topics: c_topics.length === 0 ? null : getNames(c_topics),
      technologies: c_techs.length === 0 ? null : getNames(c_techs),
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

  useEffect(() => {
    getDomains().then((res) => {
      if (fullfilled == 0) {
        fullfilled++;
        domains = res;
        domains.map(MakeOption);
      }
    });
    getTopics().then((res) => {
      if (fullfilled == 1) {
        fullfilled++;
        topics = res;
        field_name = "topics";
        topics.map(MakeOption);
      }
    });
    getTechs().then((res) => {
      if (fullfilled == 2) {
        fullfilled++;
        techs = res;
        field_name = "techs";
        techs.map(MakeOption);
      }
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

  var refreshfn = function () {
    if (c_domains.length == 0) {
      document.getElementById("domains").selectedIndex = 0;
    }
    if (c_topics.length == 0) {
      document.getElementById("topics").selectedIndex = 0;
    }
    if (c_techs.length == 0) {
      document.getElementById("techs").selectedIndex = 0;
    }
    set_rd(<Fragment>{rendered_domains.concat([])}</Fragment>);
    set_tp(<Fragment>{rendered_topics.concat([])}</Fragment>);
    set_tc(<Fragment>{rendered_techs.concat([])}</Fragment>);
    setDomns(c_domains.concat([]));
    setTopcs(c_topics.concat([]));
    setTchs(c_techs.concat([]));
  };
  function MakeOption(X) {
    const el = document.createElement("option");
    el.setAttribute("value", X.id);
    el.textContent = X.name;
    document.getElementById(field_name).appendChild(el);
  }

  function PushObj(selectedIndex, arr, c_arr, arr2) {
    if (selectedIndex > 0 && find_in_arr(selectedIndex - 1, arr, c_arr) == -1) {
      const el = (
        <ContainerLabel
          key={arr[selectedIndex - 1].name}
          innerTxt={arr[selectedIndex - 1].name}
          arr={c_arr}
          arr2={arr2}
          refresh={refreshfn}
        />
      );
      c_arr.push(arr[selectedIndex - 1]);
      arr2.push(el);
      refreshfn();
    }
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
          initialValues={{}}
          onSubmit={async (values, actions) => {
            await handleSearchForm(values, actions);
          }}>
          {(props) => (
            <Form>
              <Stack spacing={8} width={"100%"} padding={"10%"}>
                <Box>
                  <Field name="Domain">
                    {({ field, form }) => (
                      <FormControl>
                        <FormLabel>Domains</FormLabel>
                        <HStack>
                          {domains_sel}
                          <IconButton
                            icon={<RxPlus />}
                            borderRadius={"20px"}
                            onClick={() =>
                              handleArrays(
                                document.getElementById("domains")
                                  .selectedIndex,
                                domains,
                                c_domains,
                                rendered_domains,
                                setEnteredDomains
                              )
                            }
                          />
                        </HStack>
                      </FormControl>
                    )}
                  </Field>
                  {domainContainer}
                </Box>
                <Box>
                  <Field name="Topic">
                    {({ field, form }) => (
                      <FormControl id="topic">
                        <FormLabel>Topics</FormLabel>
                        <HStack>
                          {topics_sel}
                          <IconButton
                            icon={<RxPlus />}
                            borderRadius={"20px"}
                            onClick={() =>
                              handleArrays(
                                document.getElementById("topics").selectedIndex,
                                topics,
                                c_topics,
                                rendered_topics,
                                setEnteredTopics
                              )
                            }
                          />
                        </HStack>
                      </FormControl>
                    )}
                  </Field>
                  {topicContainer}
                </Box>
                <Box>
                  <Field name="Tech">
                    {({ field, form }) => (
                      <FormControl>
                        <FormLabel>Technologies</FormLabel>
                        <HStack>
                          {techs_sel}
                          <IconButton
                            icon={<RxPlus />}
                            borderRadius={"20px"}
                            onClick={() =>
                              handleArrays(
                                document.getElementById("techs").selectedIndex,
                                techs,
                                c_techs,
                                rendered_techs,
                                setEnteredTechs
                              )
                            }
                          />
                        </HStack>
                      </FormControl>
                    )}
                  </Field>
                  {techContainer}
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

export function find_in_arr(index, arr, c_arr) {
  for (var i = 0; i < c_arr.length; i++) {
    if (c_arr[i].id === arr[index].id) {
      return i;
    }
  }
  return -1;
}

function getNames(array) {
  var names = [];
  for (var i = 0; i < array.length; i++) {
    names.push(array[i].name);
  }
  return names;
}
