export default function Info({ detailImage_url }) {
  return (
    <>
      <div>
        <img width="100%" src={detailImage_url} />
      </div>
      <style jsx>{`
        div {
          margin: auto;
        }
      `}</style>
    </>
  );
}
