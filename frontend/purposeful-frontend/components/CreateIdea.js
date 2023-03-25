import { useState } from "react";
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
  FormHelperText,
  FormErrorMessage,
  InputGroup,
  InputRightElement,
  Select,
  Flex,
} from "@chakra-ui/react";
import { Field, Form, Formik } from "formik";
import { getDomains, getTechs, getTopics } from "@/utils/idea_tool";
import ContainerLabel from "./ContainerLabel";
var fullfilled = 0;
var field_name = "domains";
var c_domains = [];
var c_topics = [];
var c_techs = [];

export default function CreateIdea() {
  (async () => {
    let domains = await getDomains();
    let topics = await getTopics();
    let techs = await getTechs();
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

  var MakeOption = function(X) {
    const el = document.createElement('option');
    el.setAttribute('value',X.id);
    el.textContent = X.name;
    document.getElementById(field_name).appendChild(el);
  };

  var PushObj = function(name,arr,c_arr,elem) {
    if(find_name_in_arr(name,c_arr) == -1){
    const el = <ContainerLabel innerTxt={name} index={c_arr.length}/>;
    c_arr.push(find_name_in_arr(name,arr));
    elem.appendChild(el);
    }
  };

  return (
    <Stack width={"70%"}>
      <Box
        //width={"1"}
        rounded={"lg"}
        bg={useColorModeValue("white", "gray.700")}
        boxShadow={"lg"}
        p={8}>
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
              <Stack spacing={4} width={"50%"}>
                <Box>
                  <Field name="title">
                    {({ field, form }) => (
                      <FormControl
                        isInvalid={
                          form.errors.title && form.touched.title
                        }>
                        <FormLabel>Title</FormLabel>
                        <Input {...field} type="text" />
                        <FormErrorMessage>
                          {form.errors.title}
                        </FormErrorMessage>
                      </FormControl>
                    )}
                  </Field>
                </Box>
                <Box>
                  <Field name="purpose">
                    {({ field, form }) => (
                      <FormControl
                        id="purpose"
                        isInvalid={
                          form.errors.purpose && form.touched.purpose
                        }>
                        <FormLabel>Purpose</FormLabel>
                        <Input {...field} type="text" />
                        <FormErrorMessage>
                          {form.errors.purpose}
                        </FormErrorMessage>
                      </FormControl>
                    )}
                  </Field>
                </Box>
                <Box>
                  <Field name="description">
                    {({ field, form }) => (
                      <FormControl
                        id="description"
                        isInvalid={
                          form.errors.description && form.touched.description
                        }>
                        <FormLabel>Description</FormLabel>
                        <Textarea {...field} rows={"10"}/>
                        <FormErrorMessage>
                          {form.errors.description}
                        </FormErrorMessage>
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
                        id="domain"
                        isInvalid={
                          form.errors.domain && form.touched.domain
                        }>
                        <FormLabel>Domains</FormLabel>
                        <Select id="domains"></Select>
                        <FormErrorMessage>
                          {form.errors.domain}
                        </FormErrorMessage>
                      </FormControl>
                    )}
                  </Field>
                </Box>
                <Flex id="chosenDomains">
                  <ContainerLabel innerTxt={"Software"}/>
                </Flex>
                <Box>
                  <Field name="Topic">
                    {({ field, form }) => (
                      <FormControl
                        id="topic"
                        isInvalid={
                          form.errors.topic && form.touched.topic
                        }>
                        <FormLabel>Topics</FormLabel>
                        <Select id="topics"></Select>
                        <FormErrorMessage>
                          {form.errors.topic}
                        </FormErrorMessage>
                      </FormControl>
                    )}
                  </Field>
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
                        <Select id="techs"></Select>
                        <FormErrorMessage>
                          {form.errors.tech}
                        </FormErrorMessage>
                      </FormControl>
                    )}
                  </Field>
                </Box>
              </Stack>
              </HStack>
            </Form>
          )}
        </Formik>
      </Box>
    </Stack>
  );
}

export function find_name_in_arr(name,arr){
  for(var i = 0; i < arr.length; i++){
    if((arr[i].name).localCompare(name) == 0){
      return i;
    }
    return -1;
  }
}