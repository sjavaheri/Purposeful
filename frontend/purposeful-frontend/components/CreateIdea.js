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
} from "@chakra-ui/react";
import { Field, Form, Formik } from "formik";
import { getDomains, getTopics } from "@/utils/fetch_wrapper";
var fullfilled = 0;
export default function CreateIdea() {
  (async () => {
    let domains = await getDomains();
    let topics = await getTopics();
    if(fullfilled == 0){
      fullfilled++;
      domains.map(MakeDomains);
    }
    if(fullfilled == 1){
      fullfilled++;
      topics.map(MakeTopics);
    }
  })()

  var MakeDomains = function(X) {
    const el = document.createElement('option');
    el.setAttribute('value',X.id);
    el.textContent = X.name;
    document.getElementById("domains").appendChild(el);
  };
  var MakeTopics = function(X) {
    const el = document.createElement('option');
    el.setAttribute('value',X.id);
    el.textContent = X.name;
    document.getElementById("topics").appendChild(el);
  };
  //const [showPassword, setShowPassword] = useState(false);
  //const [showConfirmPassword, setShowConfirmPassword] = useState(false);

  // Function to validate the confirm password field
  /*
  const validateConfirmPassword = (value, password) => {
    let error;
    if (value !== password) {
      error = "Passwords do not match.";
    }
    return error;
  };
  */
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
                        <FormLabel>Domain</FormLabel>
                        <Select id="domains"></Select>
                        <FormErrorMessage>
                          {form.errors.domain}
                        </FormErrorMessage>
                      </FormControl>
                    )}
                  </Field>
                </Box>
                <Box>
                  <Field name="Topic">
                    {({ field, form }) => (
                      <FormControl
                        id="topic"
                        isInvalid={
                          form.errors.topic && form.touched.topic
                        }>
                        <FormLabel>Topic</FormLabel>
                        <Select id="topics"></Select>
                        <FormErrorMessage>
                          {form.errors.topic}
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
