USE cs246;
SELECT hr.hotelId, hr.reviewId, sc.sent_id,sc.sent, ws.sim
from
hotelReview hr,
review_sent rs,
sent_cnt sc,
(select word_y, sim from wordSim where word_x='travel' and sim>0.5) as ws
where
hr.reviewId = rs.reviewId
and hr.reviewId = sc.review_Id
and rs.sentId = sc.sent_id
and hotelId=80112
and rs.adj='good'
and rs.noun= ws.word_y
order by ws.sim desc
limit 0,100;
~             
