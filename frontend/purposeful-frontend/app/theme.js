import { extendTheme } from "@chakra-ui/react";
import { MultiSelectTheme } from "chakra-multiselect";

const config = {
  initialColorMode: "system",
  useSystemColorMode: true,
};

const theme = extendTheme({ config });

export default theme;
