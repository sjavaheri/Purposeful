import { useState } from "react";
import {
  Box,
  FormControl,
  FormLabel,
  Input,
  Stack,
  Button,
  useColorModeValue,
  FormHelperText,
  FormErrorMessage,
  HStack,
  InputGroup,
  InputRightElement,
} from "@chakra-ui/react";
import { ViewIcon, ViewOffIcon } from "@chakra-ui/icons";

export default function Registration() {
  const [showPassword, setShowPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);
  const [isError, setIsError] = useState(false); // TODO: Connect this to the error handling from the backend
  // TODO: Might need different error states for different fields
  // TODO: Connect the fields to the API form submission

  return (
    <Stack spacing={8} mx={"auto"} maxW={"2xl"} py={12} px={6}>
      <Box
        rounded={"lg"}
        bg={useColorModeValue("white", "gray.700")}
        boxShadow={"lg"}
        p={8}
      >
        <Stack spacing={4}>
          <HStack>
            <Box>
              <FormControl id="firstName" isInvalid={isError}>
                <FormLabel>First Name</FormLabel>
                <Input type="text" />
                {!isError ? null : (
                  <FormErrorMessage>First name is required.</FormErrorMessage> // TODO: Connect this to the error handling from the backend
                )}
              </FormControl>
            </Box>
            <Box>
              <FormControl id="lastName" isInvalid={isError}>
                <FormLabel>Last Name</FormLabel>
                <Input type="text" />
                {!isError ? null : (
                  <FormErrorMessage>Last name is required.</FormErrorMessage> // TODO: Connect this to the error handling from the backend
                )}
              </FormControl>
            </Box>
          </HStack>
          <FormControl id="email" isInvalid={isError}>
            <FormLabel>Username</FormLabel>
            <Input type="email" placeholder="your-email@example.com" />
            {!isError ? null : (
              <FormErrorMessage>Email is required.</FormErrorMessage> // TODO: Connect this to the error handling from the backend
            )}
          </FormControl>
          <Stack>
            <Box>
              <FormControl id="password" isInvalid={isError}>
                <FormLabel>Password</FormLabel>
                <FormHelperText mb={4}>
                  Passwords must be at least 8 characters long and contain at
                  least one number, one lowercase character and one uppercase
                  character.
                </FormHelperText>
                <InputGroup>
                  <Input
                    type={showPassword ? "text" : "password"}
                    placeholder="Password"
                  />
                  <InputRightElement h={"full"}>
                    <Button
                      variant={"ghost"}
                      onClick={() =>
                        setShowPassword((showPassword) => !showPassword)
                      }
                    >
                      {showPassword ? <ViewIcon /> : <ViewOffIcon />}
                    </Button>
                  </InputRightElement>
                </InputGroup>
                {isError ? (
                  <FormErrorMessage>Password is required.</FormErrorMessage> // TODO: Connect this to the error handling from the backend
                ) : null}
              </FormControl>
            </Box>
            <Box>
              <FormControl id="confirmPassword" isInvalid={isError}>
                <InputGroup>
                  <Input
                    type={showConfirmPassword ? "text" : "password"}
                    placeholder="Confirm password"
                  />
                  <InputRightElement h={"full"}>
                    <Button
                      variant={"ghost"}
                      onClick={() =>
                        setShowConfirmPassword(
                          (showConfirmPassword) => !showConfirmPassword
                        )
                      }
                    >
                      {showConfirmPassword ? <ViewIcon /> : <ViewOffIcon />}
                    </Button>
                  </InputRightElement>
                </InputGroup>
                {!isError ? null : (
                  <FormErrorMessage>Passwords do not match.</FormErrorMessage> // TODO: Connect this to the error handling from the backend
                )}
              </FormControl>
            </Box>
          </Stack>
        </Stack>
      </Box>
    </Stack>
  );
}
