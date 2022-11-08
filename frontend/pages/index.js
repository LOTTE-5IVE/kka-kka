import Head from "next/head";
import { useState } from "react";
import Visual from "../components/main/Visual";
import Youtube from "../components/main/Youtube";
import Introduction from "../components/main/Introduction";
import RecommSlider from "../components/main/RecommSlider";
import { useEffect } from "react";

export default function Home() {
  const [tab, setTab] = useState("리뷰");

  function handleTab(text) {
    setTab(text);
  }

  return (
    <>
      <Head>
        <title key="title">Kka-Kka | 과자 사러 과자</title>
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <link rel="icon" href="/favicon.ico" />
      </Head>
      <div className="mainWrapper">
        <div className="visual">
          <Visual />
        </div>
        <div className="recommand">
          <RecommSlider tab={tab} handleTab={handleTab} />
        </div>
        <div className="youtube">
          <Youtube />
        </div>
        <div className="introduction">
          <Introduction />
        </div>
      </div>
      <style jsx>{`
        .mainWrapper {
          display: flex;
          flex-direction: column;

          .visual {
            order: 1;
          }
          .recommand {
            order: 2;
          }
          .youtube {
            order: 3;
          }
          .introduction {
            order: 4;
          }
        }
      `}</style>
    </>
  );
}
