const functions = require('firebase-functions');
const { google } = require('googleapis');
const serviceAccount = require('./serviceAccount.json');

const SCOPES = 'https://www.googleapis.com/auth/calendar.readonly';
const CALENDAR_ID_MAP = {
  ritsuko: 'i2munc0q7oh26171odmb10bpio@group.calendar.google.com',
  producer: 'qpd7ie37km0ii81rmp2s08l79c@group.calendar.google.com',
  junjirou: '6agspm4p1im0k28b19mmueb8hk@group.calendar.google.com',
};
const auth = new google.auth.JWT(
  serviceAccount['client_email'],
  null,
  serviceAccount['private_key'],
  SCOPES,
  null,
);

const api = google.calendar({ version: 'v3', auth });

function eventList(calendarApi, calendarId, from, to) {
  const params = {
    calendarId,
    timeZone: 'Asia/Tokyo',
    timeMin: from,
    timeMax: to,
  };

  return new Promise((resolve, reject) => {
    calendarApi.events.list(params, (err, res) => {
      if (err) {
        console.log(err);
        reject(err);
      } else {
        resolve(res.data.items);
      }
    });
  });
}

exports.schedules = functions.https.onRequest((req, res) => {
  if (req.method !== 'GET') {
    res.status(405).send('Method Not Allowed');
    return;
  }

  const calendarIds = req.query.names.map(name => CALENDAR_ID_MAP[name]);

  Promise.all(calendarIds.map(id => eventList(api, id, req.query.from, req.query.to)))
    .then(events => res.send(events))
    .catch(e => res.status(500).send(e.message));
});

/*
* names[]=ritsuko&names[]=producer&names[]=junjirou&from=2019-12-10T18:00:00%2B09:00&to=2019-12-10T23:59:59%2B09:00
* */


