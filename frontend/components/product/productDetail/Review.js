export default function Review({ reviews }) {
  return (
    <>
      <div className="tableWrapper">
        <table>
          <colgroup>
            <col style={{ width: "10vw" }} />
            <col style={{ width: "30vw" }} />
            <col style={{ width: "7vw" }} />
            <col style={{ width: "20vw" }} />
          </colgroup>
          <tbody>
            {reviews?.data.map((review, index) => {
              return (
                <tr style={{ height: "5vw" }}>
                  <td>별점</td>
                  <td>{review.contents}</td>
                  <td>{review.member.name}</td>
                  <td>{review.createdAt}</td>
                </tr>
              );
            })}
          </tbody>
        </table>
      </div>
      <style jsx>{`
        .tableWrapper {
          width: 90%;
          margin: 0 auto 150px;

          table {
            border-collapse: collapse;

            tr {
              border-top: 1px solid #dfdfdf;
              border-bottom: 1px solid #dfdfdf;
            }
          }
        }
      `}</style>
    </>
  );
}
