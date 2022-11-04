import Head from "next/head";

export default function Title({ title }) {
  const context = `Kka-Kka | ${title}`;

  return (
    <>
      <Head>
        <title>{context}</title>
      </Head>
    </>
  );
}

Title.defaultProps = {
  title: "과자 사러 과자",
};
