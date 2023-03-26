// By default, server components
// Since we want interaction with the user,
// we need to use client components
// see https://beta.nextjs.org/docs/rendering/server-and-client-components#when-to-use-server-vs-client-components
"use client";
import {
  Button,
  Flex,
  Heading,
  Stack,
  useColorMode,
  useColorModeValue,
} from "@chakra-ui/react";
import {
  ModifyDetails,
  ModifyPassword,
} from "@/components/ModifyAccountDetails";
import { verifyToken, logout, getAuthorities } from "@/utils/fetch_wrapper";
import { useState, useEffect } from "react";

export default function ModifyAccountDetailsPage() {
  // User and authentication info
  // from Siger
  const [appUser, setAppUser] = useState(null);
  const [GrantedAuth, setGrantedAuth] = useState([]);
  const [verified, setVerified] = useState(false);
  useEffect(() => {
    verifyToken().then((res) => {
      setVerified(res);
      setAppUser(localStorage.getItem("appUser"));
      setGrantedAuth(getAuthorities());
    });
  }, []);

  return (
    <Flex
      minH={"100vh"}
      align={"center"}
      justify={"center"}
      bg={useColorModeValue("gray.50", "gray.800")}>
      <Stack spacing={4} mx={"auto"} maxW={"2xl"} py={12} px={6}>
        <Heading as="h1" size="2xl" align="center">
          Modify your account information below
        </Heading>
        <ModifyDetails
          appUser={JSON.parse(appUser)}
          GrantedAuth={GrantedAuth}
        />
        <ModifyPassword
          appUser={JSON.parse(appUser)}
          GrantedAuth={GrantedAuth}
        />
      </Stack>
    </Flex>
  );
}
