"use client";

import { CacheProvider } from "@chakra-ui/next-js";
import { ChakraProvider, Box } from "@chakra-ui/react";
import theme from "./theme";
import NavBar from "@/components/NavBar";

export default function RootLayout({ children }) {
  return (
    <html lang="en">
      <head />
      <body>
        <CacheProvider>
          <ChakraProvider theme={theme}>
            <Box position={"sticky"} top={"0px"} width={"100%"} zIndex={"3"}>
              <NavBar />
            </Box>
            {children}
          </ChakraProvider>
        </CacheProvider>
      </body>
    </html>
  );
}
