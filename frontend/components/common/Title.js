import Head from "next/head";

export default function Title({ title }) {
  return (
    <>
      <Head>
        <title>Kka-Kka | {title}</title>
      </Head>
    </>
  );
}
