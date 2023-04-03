// By default, server components
// Since we want interaction with the user,
// we need to use client components
// see https://beta.nextjs.org/docs/rendering/server-and-client-components#when-to-use-server-vs-client-components
"use client";
import {
  Button,
  Flex,
  Heading,
  Link,
  Stack,
  Text,
  useColorMode,
  useColorModeValue,
  Box,
} from "@chakra-ui/react";
import ModifyIdea from "@/components/ModifyIdea";
import { useSearchParams } from 'next/navigation';
import { useEffect, useState } from "react";
import { getIdeaFromID } from "@/utils/idea_tool";
import { Spinner } from '@chakra-ui/react'


export default function ModifyIdeaPage() {
  const { colorMode, toggleColorMode } = useColorMode(); // TODO: Move the light/dark mode toggle button to the navigation header
  const searchParams = useSearchParams();
  const id = searchParams.get('ideaId');

  const [comp,setComp] = useState(<Box><Spinner/></Box>);
  useEffect(() => {
    getIdeaFromID(id).then((res) => {
      console.log(res);
      console.log(res.title);
      setComp(<ModifyIdea ideaId={id} oldTitle={res.title} oldPurpose={res.purpose} oldDescription={res.description} oldIconUrl={res.iconUrl.url} oldisPaid={res.isPaid} oldinProgress={res.inProgress} oldisPrivate={res.isPrivate}/>);
    });
  }, []);

  return (
    <Flex
      minH={"100vh"}
      align={"center"}
      justify={"center"}
      bg={useColorModeValue("gray.50", "gray.800")}
    >
    {comp}
    </Flex>
  );
}