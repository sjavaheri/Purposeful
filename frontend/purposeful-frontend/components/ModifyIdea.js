import { Fragment, useState } from "react";
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

import {
    DeleteIcon
  } from "@chakra-ui/icons";
import { Field, Form, Formik } from "formik";
import { getDomains, getTechs, getTopics } from "@/utils/idea_tool";
import ContainerLabel from "./ContainerLabel";
import { RxPlus } from "react-icons/rx";


var fullfilled = 0;
var field_name = "domains";
var c_domains = [];
var c_topics = [];
var c_techs = [];

var domains_sel = <Select id="domains"></Select>
var topics_sel = <Select id="topics"></Select>
var techs_sel = <Select id="techs"></Select>

var rendered_domains = [];
var rendered_topics = [];
var rendered_techs = [];

export default function ModifyIdea() {
  const [render_domains, set_rd] = useState(<Fragment></Fragment>);
  const [render_topics, set_tp] = useState(<Fragment></Fragment>);
  const [render_techs, set_tc] = useState(<Fragment></Fragment>);

  var domainContainer = <Stack on direction={['column', 'row']} id={"domainContainer"} wrap={"wrap"} justifyContent={"center"}>{render_domains}</Stack>;
  var topicContainer = <Stack on direction={['column', 'row']} id={"topicContainer"} wrap={"wrap"} justifyContent={"center"}>{render_topics}</Stack>;
  var techContainer = <Stack on direction={['column', 'row']} id={"techContainer"} wrap={"wrap"} justifyContent={"center"}>{render_techs}</Stack>;
  var domains = [];
  var topics = [];
  var techs = [];

  (async () => {
    domains = await getDomains();
    topics = await getTopics();
    techs = await getTechs();
    if(fullfilled == 0){
      fullfilled++;
      domains.map(MakeOption);
    }
    if(fullfilled == 1){
      fullfilled++;
      field_name = "topics";
      topics.map(MakeOption);
    }
    if(fullfilled == 2){
      fullfilled++;
      field_name = "techs";
      techs.map(MakeOption);
    }
  })()

  var refreshfn = function(){
    set_rd(<Fragment>{rendered_domains.concat([])}</Fragment>);
    set_tp(<Fragment>{rendered_topics.concat([])}</Fragment>);
    set_tc(<Fragment>{rendered_techs.concat([])}</Fragment>);
  }
  function MakeOption(X) {
    const el = document.createElement('option');
    el.setAttribute('value',X.id);
    el.textContent = X.name;
    document.getElementById(field_name).appendChild(el);
  };
  
  function PushObj(selectedIndex,arr,c_arr, arr2) {
    if(find_in_arr(selectedIndex,arr,c_arr) == -1){
    const el = <ContainerLabel key={arr[selectedIndex].name} innerTxt={arr[selectedIndex].name} arr={c_arr} arr2={arr2} refresh={refreshfn}/>;
    c_arr.push(arr[selectedIndex]);
    arr2.push(el)
    refreshfn();
    }
  };
  return (
    <Stack width={"75%"}>
      <Box
        //width={"1"}
        rounded={"lg"}
        bg={useColorModeValue("white", "gray.700")}
        boxShadow={"lg"}
        p={8}>
        <Formik
          initialValues={{
            title: "Real Idea Title",
            purpose: "Real Idea Purpose",
            description: "Detailed Description",
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
              <Stack spacing={4} width={"50%"}>
                <Box>
                  <Field name="title">
                    {({ field, form }) => (
                      <FormControl>
                        <FormLabel>Title</FormLabel>
                        <Input {...field} type="text" />
                      </FormControl>
                    )}
                  </Field>
                </Box>
                <Box>
                  <Field name="purpose">
                    {({ field, form }) => (
                      <FormControl
                        id="purpose">
                        <FormLabel>Purpose</FormLabel>
                        <Input {...field} type="text" />
                      </FormControl>
                    )}
                  </Field>
                </Box>
                <Box>
                  <Field name="description">
                    {({ field, form }) => (
                      <FormControl
                        id="description">
                        <FormLabel>Description</FormLabel>
                        <Textarea {...field} rows={"10"}/>
                      </FormControl>
                    )}
                  </Field>
                </Box>
              </Stack>
              <Stack spacing={4} width={"50%"} padding={"50px"}>
                <Checkbox name="ispaid">Paid</Checkbox>
                <Checkbox name="inprogress">In Progress</Checkbox>
                <Checkbox name="isprivate">Private</Checkbox>
                <Box>
                  <Field name="Domain">
                    {({ field, form }) => (
                      <FormControl
                        id="domain">
                        <FormLabel>Domains</FormLabel>
                        <HStack>
                        {domains_sel}
                        <IconButton
                          icon={<RxPlus/>}
                          borderRadius={"20px"}
                          onClick={() => PushObj(document.getElementById('domains').selectedIndex, domains,c_domains, rendered_domains)}
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
                      <FormControl
                        id="topic"
                        isInvalid={
                          form.errors.topic && form.touched.topic
                        }>
                        <FormLabel>Topics</FormLabel>
                        <HStack>
                        {topics_sel}
                        <IconButton
                          icon={<RxPlus/>}
                          borderRadius={"20px"}
                          onClick={() => PushObj(document.getElementById('topics').selectedIndex, topics,c_topics,rendered_topics)}
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
                      <FormControl
                        id="tech"
                        isInvalid={
                          form.errors.tech && form.touched.tech
                        }>
                        <FormLabel>Technologies</FormLabel>
                        <HStack>
                          {techs_sel}
                          <IconButton
                            icon={<RxPlus/>}
                            borderRadius={"20px"}
                            onClick={() => PushObj(document.getElementById('techs').selectedIndex, techs,c_techs,rendered_techs)}
                          />
                        </HStack>
                      </FormControl>
                    )}
                  </Field>
                  {techContainer}
                </Box>
              </Stack>
              </HStack>
              <Button
                bg={"blue.400"}
                color={"white"}
                _hover={{
                  bg: "blue.500",
                }}
                type="submit">
                Save Changes
              </Button>
              <Button
                bg={"red.200"}
                color={"red"}
                _hover={{
                    bg: "red.400",
                }}
                type="submit"
                style={{float : 'right'}}>
                <DeleteIcon w={8} h={8} color="red.500" />
              </Button>
            </Form>
          )}
        </Formik>
      </Box>
    </Stack>
  );
}

export function find_in_arr(index,arr,c_arr){
  for(var i = 0; i < c_arr.length; i++){
    if((c_arr[i].id) === (arr[index]).id){
      return i;
    }
  }
  return -1;
}