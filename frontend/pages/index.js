import Head from "next/head";
import { useState } from "react";
import Title from "../components/common/Title";
import Visual from "../components/main/Visual";
import Youtube from "../components/main/Youtube";
import Introduction from "../components/main/Introduction";
import RecommSlider from "../components/main/RecommSlider";

export default function Home() {
  const [tab, setTab] = useState("리뷰");

  function handleTab(text) {
    setTab(text);
  }

  return (
    <>
      <Head>
        <title>kka-kka</title>
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <link rel="icon" href="/favicon.ico" />
      </Head>
      <Title title="과자 사러 과자" />
      <Visual />
      <RecommSlider tab={tab} handleTab={handleTab} />
      <Youtube />
      <Introduction />
    </>
  );
}
